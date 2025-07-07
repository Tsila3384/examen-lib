package mg.itu.library.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mg.itu.library.model.Abonnement;
import mg.itu.library.model.Personne;
import mg.itu.library.repository.AbonnementRepository;

@Service
public class AbonnementService {
    private final AbonnementRepository repository;
    public AbonnementService(AbonnementRepository repository) { this.repository = repository; }
    public List<Abonnement> findAll() { return repository.findAll(); }
    public Optional<Abonnement> findById(Long id) { return repository.findById(id); }
    public Abonnement save(Abonnement a) { return repository.save(a); }
    public void deleteById(Long id) { repository.deleteById(id); }

    /**
     * Vérifie si la personne a un abonnement actif à la date donnée
     */
    public boolean hasAbonnementActif(Personne personne, LocalDateTime date) {
        return repository.findFirstByPersonneAndDateAbonnementLessThanEqualAndDateExpirationGreaterThanEqual(
                personne, date, date).isPresent();
    }
}
