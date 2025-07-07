package mg.itu.library.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mg.itu.library.dto.AdherentInscriptionRequest;
import mg.itu.library.model.Adherent;
import mg.itu.library.model.Abonnement;
import mg.itu.library.model.Personne;
import mg.itu.library.model.TypePersonne;
import mg.itu.library.repository.AdherentRepository;
import mg.itu.library.repository.PersonneRepository;
import mg.itu.library.repository.TypePersonneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AdherentService {
    private final AdherentRepository repository;
    private final TypePersonneRepository typePersonneRepository;
    private final PersonneRepository personneRepository;
    private final AbonnementService abonnementService;

    @PersistenceContext
    private EntityManager entityManager;

    public AdherentService(AdherentRepository repository, TypePersonneRepository typePersonneRepository, PersonneRepository personneRepository, AbonnementService abonnementService) {
        this.repository = repository;
        this.typePersonneRepository = typePersonneRepository;
        this.personneRepository = personneRepository;
        this.abonnementService = abonnementService;
    }

    
    public List<Adherent> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Adherent inscrireNouvelAdherent(AdherentInscriptionRequest req) {
        if (req.getEmail() == null || req.getMotDePasse() == null || req.getTypePersonneId() == null) {
            throw new IllegalArgumentException("Veuillez compléter tous les champs requis");
        }

        TypePersonne type = typePersonneRepository.findById(req.getTypePersonneId()).orElse(null);
        if (type == null) {
            throw new IllegalArgumentException("Type de personne invalide");
        }

        Adherent adherent;
        if (req.isDejaPersonne()) {
            // Supprime la personne existante pour la recréer comme Adherent
            Personne personneExistante = personneRepository.findAll().stream()
                .filter(p -> req.getEmail().equalsIgnoreCase(p.getEmail()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aucune personne existante avec cet email. Veuillez décocher la case ou vérifier l'email."));
            // Vérification via la base : la personne n'est pas un bibliothécaire
            boolean isBibliothecaire = entityManager.createQuery(
                "SELECT COUNT(b) FROM Bibliothecaire b WHERE b.id = :id", Long.class)
                .setParameter("id", personneExistante.getId())
                .getSingleResult() > 0;
            if (isBibliothecaire) {
                throw new IllegalArgumentException("Impossible de promouvoir un bibliothécaire en adhérent.");
            }
            
            personneRepository.deleteById(personneExistante.getId());
            entityManager.flush();
            entityManager.clear();
            adherent = new Adherent();
            adherent.setNom(personneExistante.getNom());
            adherent.setPrenom(personneExistante.getPrenom());
            adherent.setEmail(personneExistante.getEmail());
            adherent.setAdresse(personneExistante.getAdresse());
            adherent.setDateNaissance(personneExistante.getDateNaissance());
        } else {
            // Nouvelle personne : NE PAS setter l'id, laisser JPA le générer
            adherent = new Adherent();
            adherent.setNom(req.getNom());
            adherent.setPrenom(req.getPrenom());
            adherent.setEmail(req.getEmail());
            adherent.setAdresse(req.getAdresse());
            adherent.setDateNaissance(req.getDateNaissance());
        }

        adherent.setNumeroAdherent(UUID.randomUUID().toString());
        adherent.setDateInscription(LocalDate.now());
        adherent.setStatut("actif");
        adherent.setMotDePasse(req.getMotDePasse());
        adherent.setTypePersonne(type);

        Adherent savedAdherent = repository.save(adherent);

        // Création automatique d'un abonnement d'un mois
        Abonnement abonnement = new Abonnement();
        abonnement.setPersonne(savedAdherent);
        abonnement.setDateAbonnement(LocalDateTime.now());
        abonnement.setDateExpiration(LocalDateTime.now().plusMonths(1));
        abonnementService.save(abonnement);

        return savedAdherent;
    }
}
