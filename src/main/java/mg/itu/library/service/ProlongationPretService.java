
package mg.itu.library.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mg.itu.library.model.Bibliothecaire;
import mg.itu.library.model.Penalite;
import mg.itu.library.model.Pret;
import mg.itu.library.model.PretHistorique;
import mg.itu.library.model.ProlongationPret;
import mg.itu.library.model.Quota;
import mg.itu.library.model.StatutPret;
import mg.itu.library.model.StatutProlongation;
import mg.itu.library.model.StatutValidation;
import mg.itu.library.repository.BibliothecaireRepository;
import mg.itu.library.repository.PenaliteRepository;
import mg.itu.library.repository.PretHistoriqueRepository;
import mg.itu.library.repository.PretRepository;
import mg.itu.library.repository.ProlongationPretRepository;
import mg.itu.library.repository.QuotaRepository;
import mg.itu.library.repository.StatutPretRepository;
import mg.itu.library.repository.StatutProlongationRepository;
import mg.itu.library.repository.StatutValidationRepository;

@Service
public class ProlongationPretService {
    private final ProlongationPretRepository repository;
    private final PretRepository pretRepository;
    private final QuotaRepository quotaRepository;
    private final StatutValidationRepository statutValidationRepository;
    private final StatutProlongationRepository statutProlongationRepository;
    private final BibliothecaireRepository bibliothecaireRepository;
    private final StatutPretRepository statutPretRepository;
    private final PretHistoriqueRepository pretHistoriqueRepository;
    private final PenaliteRepository penaliteRepository;
    private final AbonnementService abonnementService;

    public ProlongationPretService(ProlongationPretRepository repository, PretRepository pretRepository, QuotaRepository quotaRepository,
                                   StatutValidationRepository statutValidationRepository,
                                   StatutProlongationRepository statutProlongationRepository,
                                   BibliothecaireRepository bibliothecaireRepository,
                                   StatutPretRepository statutPretRepository, PretHistoriqueRepository pretHistoriqueRepository,
                                   PenaliteRepository penaliteRepository, AbonnementService abonnementService) {
        this.repository = repository;
        this.pretRepository = pretRepository;
        this.quotaRepository = quotaRepository;
        this.statutValidationRepository = statutValidationRepository;
        this.statutProlongationRepository = statutProlongationRepository;
        this.bibliothecaireRepository = bibliothecaireRepository;
        this.statutPretRepository = statutPretRepository;
        this.pretHistoriqueRepository = pretHistoriqueRepository;
        this.penaliteRepository = penaliteRepository;
        this.abonnementService = abonnementService;
    }

    public List<ProlongationPret> findAll() {
        return repository.findAll();
    }

    /**
     * Vérifie si un prêt est éligible à la prolongation (accepté, non rendu, pas déjà prolongé)
     */
    public boolean isPretEligibleProlongation(Pret pret) {
        // Seuls les prêts ayant un statut de validation 'acceptée' (2) peuvent être prolongés
        if (pret == null) return false;
        if (pret.getStatutValidation() == null || pret.getStatutValidation().getId() != 2) return false;
        // Optionnel : vérifier aussi que le prêt n'est pas rendu (statut_pret_id == 1 = en cours)
        if (pret.getStatutPret().getId() == null || pret.getStatutPret().getId() != 1) return false;
        // Vérifier qu'il n'existe pas déjà une prolongation pour ce prêt
        return repository.findAll().stream().noneMatch(p -> p.getPret().getId().equals(pret.getId()));
    }

    /**
     * Liste les prêts éligibles à la prolongation pour un adhérent donné
     */
    public List<Pret> findPretsEligiblesProlongation(Long adherentId) {
        StatutPret statutRendu = statutPretRepository.findByLibelleIgnoreCase("rendu");
        List<Pret> prets = pretRepository.findByAdherentIdAndStatutPretNot(adherentId, statutRendu);

        return prets.stream().filter(this::isPretEligibleProlongation).toList();
    }

    /**
     * Crée une demande de prolongation pour un prêt donné
     */
    @Transactional
    public String demanderProlongation(Long pretId) {
        Pret pret = pretRepository.findById(pretId).orElse(null);
        if (pret == null) return "Prêt introuvable.";
        if (!isPretEligibleProlongation(pret)) return "Ce prêt n'est pas éligible à la prolongation.";

        // Vérification pénalité en cours
        List<Penalite> penalites = penaliteRepository.findByPersonne(pret.getAdherent());
        java.time.LocalDate today = java.time.LocalDate.now();
        Penalite penaliteActive = penalites.stream()
            .filter(p -> p.getDateDebut() != null && p.getDateFin() != null &&
                !today.isBefore(p.getDateDebut()) && !today.isAfter(p.getDateFin()))
            .findFirst().orElse(null);
        if (penaliteActive != null) {
            return "Vous êtes actuellement sous pénalité du "
                + penaliteActive.getDateDebut() + " au " + penaliteActive.getDateFin()
                + " et ne pouvez pas demander de prolongation.";
        }
        // Vérification abonnement actif
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        if (!abonnementService.hasAbonnementActif(pret.getAdherent(), now)) {
            return "Vous n'avez pas d'abonnement actif à la date de la demande de prolongation. Veuillez renouveler votre abonnement.";
        }

        // Calcul de la nouvelle date de retour (on ajoute le nombre de jours du quota)
        Quota quota = quotaRepository.findAll().stream()
            .filter(q -> q.getTypePersonne().equals(pret.getAdherent().getTypePersonne()))
            .findFirst().orElse(null);
        int nbJours = (quota != null && quota.getNombreJourProlongement() != null) ? quota.getNombreJourProlongement() : 7;
        ProlongationPret prolong = new ProlongationPret();
        prolong.setPret(pret);
        prolong.setDateProlongation(now);
        prolong.setNouvelleDateRetour(pret.getDateRetourPrevue().plusDays(nbJours));
        // Initialiser le statut à "en cours de validation" (id = 1)
        StatutValidation statutEnCours = statutValidationRepository.findById(1L).orElse(null);
        if (statutEnCours == null) {
            return "Erreur technique : le statut 'en cours de validation' est introuvable.";
        }

        prolong.setStatut(statutEnCours);
        repository.save(prolong);

        return "Demande de prolongation enregistrée (en cours de validation).";
    }

    // Liste les prolongations en attente (statut_id = 1)
    public List<ProlongationPret> findProlongationsEnAttente() {
        return repository.findAll().stream()
            .filter(p -> p.getStatut() != null && p.getStatut().getId() == 1)
            .toList();
    }

    // Valide ou refuse une demande de prolongation
    @Transactional
    public String validerProlongation(Long prolongationId, String action, Long biblioId) {
        ProlongationPret prolong = repository.findById(prolongationId).orElse(null);
        if (prolong == null) return "Prolongation introuvable.";
        Pret pret = prolong.getPret();

        if (pret == null) return "Prêt associé introuvable.";
        Quota quota = quotaRepository.findAll().stream()
            .filter(q -> q.getTypePersonne().equals(pret.getAdherent().getTypePersonne()))
            .findFirst().orElse(null);
        int nbJours = (quota != null && quota.getNombreJourProlongement() != null) ? quota.getNombreJourProlongement() : 7;
        LocalDateTime now = LocalDateTime.now();

        StatutValidation statut;
        // Prendre l'ancien statut de la prolongation AVANT modification
        StatutValidation oldStatutProlong = prolong.getStatut();

        // Insérer dans statut_prolongation (historique)
        StatutProlongation sp = new StatutProlongation();
        sp.setProlongation(prolong);
        sp.setStatut(oldStatutProlong);

        Bibliothecaire biblio = bibliothecaireRepository.findById(biblioId).orElse(null);
        if (biblio == null) {
            throw new IllegalStateException("Aucun bibliothécaire trouvé pour l'action. Veuillez vous reconnecter.");
        }

        sp.setBibliothecaire(biblio);
        sp.setDateFin(now);
        statutProlongationRepository.save(sp);

        if ("accepter".equals(action)) {
            statut = statutValidationRepository.findById(2L).orElse(null); // 2 = acceptée
            StatutPret enProlongStatut = statutPretRepository.findByLibelleIgnoreCase("en prolongement");
            pret.setStatutPret(enProlongStatut);

            StatutPret enCoursStatut = statutPretRepository.findByLibelleIgnoreCase("en cours");

            PretHistorique pretHistorique = new PretHistorique();
            pretHistorique.setPret(pret);
            pretHistorique.setStatutPret(enCoursStatut);
            pretHistorique.setDateFinStatut(LocalDateTime.now());

            pretHistoriqueRepository.save(pretHistorique);
            // Mettre à jour la date de retour du prêt
            //pret.setDateRetourPrevue(pret.getDateRetourPrevue().plusDays(nbJours));
        } else {
            statut = statutValidationRepository.findById(3L).orElse(null); // 3 = refusée
        }

        // Mettre à jour le statut de la prolongation
        prolong.setStatut(statut);
        repository.save(prolong);

        // Mettre à jour le prêt avec le nouveau statut (refusé ou accepté)
        //pret.setStatutValidation(statut);
        pretRepository.save(pret);

        return (statut != null && statut.getId() == 2L) ? "Prolongation acceptée." : "Prolongation refusée.";
    }
}
