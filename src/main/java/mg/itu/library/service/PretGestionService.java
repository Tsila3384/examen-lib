package mg.itu.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mg.itu.library.model.Adherent;
import mg.itu.library.model.Bibliothecaire;
import mg.itu.library.model.Exemplaire;
import mg.itu.library.model.Livre;
import mg.itu.library.model.Penalite;
import mg.itu.library.model.Pret;
import mg.itu.library.model.ProlongationPret;
import mg.itu.library.model.Quota;
import mg.itu.library.model.RestrictionLivre;
import mg.itu.library.model.StatutDisponibilite;
import mg.itu.library.model.StatutPret;
import mg.itu.library.model.StatutValidation;
import mg.itu.library.model.StatutValidationPret;
import mg.itu.library.repository.AdherentRepository;
import mg.itu.library.repository.BibliothecaireRepository;
import mg.itu.library.repository.ExemplaireRepository;
import mg.itu.library.repository.LivreRepository;
import mg.itu.library.repository.PenaliteRepository;
import mg.itu.library.repository.PretHistoriqueRepository;
import mg.itu.library.repository.PretRepository;
import mg.itu.library.repository.ProlongationPretRepository;
import mg.itu.library.repository.QuotaRepository;
import mg.itu.library.repository.RestrictionLivreRepository;
import mg.itu.library.repository.StatutDisponibiliteRepository;
import mg.itu.library.repository.StatutPretRepository;
import mg.itu.library.repository.StatutValidationPretRepository;
import mg.itu.library.repository.StatutValidationRepository;

@Service
public class PretGestionService {
    private final PretRepository pretRepo;
    private final LivreRepository livreRepo;
    private final ExemplaireRepository exemplaireRepo;
    private final AdherentRepository adherentRepo;
    private final BibliothecaireRepository bibliothecaireRepository;
    private final StatutDisponibiliteRepository statutDisponibiliteRepository;
    private final QuotaRepository quotaRepository;
    private final StatutValidationPretRepository statutValidationPretRepository;
    private final StatutValidationRepository statutValidationRepository;
    private final StatutPretRepository statutPretRepository;
    private final PenaliteRepository penaliteRepository;
    private final RestrictionLivreRepository restrictionLivreRepository;
    private final ProlongationPretRepository prolongationPretRepository;
    private final PenaliteService penaliteService;
    private final JourFerieService jourFerieService;
    private final mg.itu.library.repository.ConfigurationRepository configurationRepository;
    private final PretHistoriqueRepository pretHistoriqueRepository;
    private final AbonnementService abonnementService;

    public PretGestionService(PretRepository pretRepo,
            LivreRepository livreRepo,
            ExemplaireRepository exemplaireRepo,
            AdherentRepository adherentRepo,
            BibliothecaireRepository bibliothecaireRepository,
            StatutDisponibiliteRepository statutDisponibiliteRepository,
            QuotaRepository quotaRepository,
            StatutValidationPretRepository statutValidationPretRepository,
            StatutValidationRepository statutValidationRepository,
            StatutPretRepository statutPretRepository,
            PenaliteRepository penaliteRepository,
            RestrictionLivreRepository restrictionLivreRepository,
            ProlongationPretRepository prolongationPretRepository,
            PenaliteService penaliteService,
            JourFerieService jourFerieService,
            mg.itu.library.repository.ConfigurationRepository configurationRepository,
            PretHistoriqueRepository pretHistoriqueRepository,
            AbonnementService abonnementService) {
        this.pretRepo = pretRepo;
        this.livreRepo = livreRepo;
        this.exemplaireRepo = exemplaireRepo;
        this.adherentRepo = adherentRepo;
        this.bibliothecaireRepository = bibliothecaireRepository;
        this.statutDisponibiliteRepository = statutDisponibiliteRepository;
        this.quotaRepository = quotaRepository;
        this.statutValidationPretRepository = statutValidationPretRepository;
        this.statutValidationRepository = statutValidationRepository;
        this.statutPretRepository = statutPretRepository;
        this.penaliteRepository = penaliteRepository;
        this.restrictionLivreRepository = restrictionLivreRepository;
        this.prolongationPretRepository = prolongationPretRepository;
        this.penaliteService = penaliteService;
        this.jourFerieService = jourFerieService;
        this.configurationRepository = configurationRepository;
        this.pretHistoriqueRepository = pretHistoriqueRepository;
        this.abonnementService = abonnementService;
    }

    /**
     * Retourne le statut "rendu".
     */
    public StatutPret getStatutPretRendu() {
        // ancien nom : statutPretRepository
        var repoStatutPret = this.statutPretRepository;
        return repoStatutPret.findByLibelleIgnoreCase("rendu");
    }

    /**
     * Liste tous les prêts non rendus pour un adhérent donné.
     */
    public java.util.List<Pret> getPretsNonRendusPourAdherent(Long idAdherent, StatutPret statutRendu) {
        return pretRepo.findByAdherentIdAndStatutPretNot(idAdherent, statutRendu);
    }

    /**
     * Rend un prêt : passe le statut à "rendu" et historise l'ancien statut.
     */
    @org.springframework.transaction.annotation.Transactional
    public void rendrePret(Long pretId) {
        Pret pret = pretRepo.findById(pretId).orElseThrow(() -> new IllegalArgumentException("Prêt introuvable"));
        StatutPret statutRendu = statutPretRepository.findByLibelleIgnoreCase("rendu");
        if (statutRendu == null)
            throw new IllegalStateException("Statut 'rendu' introuvable");
        StatutPret ancienStatut = pret.getStatutPret();
        if (ancienStatut == null || ancienStatut.getLibelle().equalsIgnoreCase("rendu")) {
            throw new IllegalStateException("Ce prêt est déjà rendu ou n'a pas de statut valide.");
        }
        // Historiser l'ancien statut
        mg.itu.library.model.PretHistorique historique = new mg.itu.library.model.PretHistorique();
        historique.setPret(pret);
        historique.setStatutPret(ancienStatut);
        historique.setDateFinStatut(java.time.LocalDateTime.now());
        pretHistoriqueRepository.save(historique);
        // Mettre à jour le prêt
        pret.setStatutPret(statutRendu);
        pretRepo.save(pret);
    }

    /**
     * Création d'un prêt à partir d'une réservation passée à "prévu".
     * Remplit les champs nécessaires, applique la logique métier, et sauvegarde le
     * prêt.
     */
    public Pret creerPretDepuisReservation(Pret pret) {
        if (pret.getAdherent() == null || pret.getExemplaire() == null) {
            throw new IllegalArgumentException("Adhérent ou exemplaire manquant pour le prêt");
        }
        // Calcul de la durée de prêt selon le quota de l'adhérent
        Quota quota = getQuotaForAdherent(pret.getAdherent());
        Integer nbJoursPret = quota != null ? quota.getNombreJourPret() : null;
        if (nbJoursPret == null) {
            throw new IllegalStateException("Aucune durée de prêt définie pour ce type d'adhérent");
        }
        // Si la date d'emprunt n'est pas définie, on prend maintenant
        if (pret.getDateEmprunt() == null) {
            pret.setDateEmprunt(java.time.LocalDateTime.now());
        }
        pret.setDateRetourPrevue(pret.getDateEmprunt().plusDays(nbJoursPret));
        // Statut validation : acceptée (id=2)
        StatutValidation statutValide = statutValidationRepository.findById(2L).orElse(null);
        pret.setStatutValidation(statutValide);
        // Statut prêt : en cours
        StatutPret statutPret = statutPretRepository.findAll().stream()
                .filter(sp -> sp.getLibelle() != null && sp.getLibelle().equalsIgnoreCase("en cours"))
                .findFirst().orElse(null);
        pret.setStatutPret(statutPret);
        // Sauvegarde
        pretRepo.save(pret);
        return pret;
    }

    /**
     * Fonctionnalité d'emprunt de livre (front office)
     * Ajout de la possibilité de spécifier la date d'emprunt.
     */
    public Pret emprunterLivre(Long adherentId, Long livreId, String typePret, java.time.LocalDateTime dateEmprunt) {
        Adherent adherent = adherentRepo.findById(adherentId)
                .orElseThrow(() -> new IllegalArgumentException("Adhérent introuvable"));
        // Livre livre = livreRepo.findById(livreId)
        // .orElseThrow(() -> new IllegalArgumentException("Livre introuvable"));
        // (ligne commentée car la variable n'est pas utilisée)

        // Vérification pénalité en cours
        List<Penalite> penalites = penaliteRepository.findByPersonne(adherent);
        LocalDate today = LocalDate.now();
        Penalite penaliteActive = penalites.stream()
                .filter(p -> p.getDateDebut() != null && p.getDateFin() != null &&
                        !today.isBefore(p.getDateDebut()) && !today.isAfter(p.getDateFin()))
                .findFirst().orElse(null);
        if (penaliteActive != null) {
            throw new IllegalStateException("Vous êtes actuellement sous pénalité du "
                    + penaliteActive.getDateDebut() + " au " + penaliteActive.getDateFin()
                    + " et ne pouvez pas emprunter de livre.");
        }

        // Vérification abonnement actif
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        if (!abonnementService.hasAbonnementActif(adherent, now)) {
            throw new IllegalStateException(
                    "Vous n'avez pas d'abonnement actif à la date de l'emprunt. Veuillez renouveler votre abonnement.");
        }

        Exemplaire exemplaireDispo = findExemplaireDisponible(livreId);
        if (exemplaireDispo == null) {
            throw new IllegalStateException("Aucun exemplaire disponible pour ce livre");
        }
        Quota quota = getQuotaForAdherent(adherent);
        Integer nbJoursPret = quota.getNombreJourPret();
        if (nbJoursPret == null) {
            throw new IllegalStateException("Aucune durée de prêt définie pour ce type d'adhérent");
        }
        // Création du prêt en attente de validation
        Pret pret = new Pret();
        pret.setAdherent(adherent);
        pret.setExemplaire(exemplaireDispo);
        pret.setTypePret(typePret);
        // Utiliser la date passée ou la date actuelle
        java.time.LocalDateTime dateEmpruntEffective = (dateEmprunt != null) ? dateEmprunt
                : java.time.LocalDateTime.now();
        pret.setDateEmprunt(dateEmpruntEffective);
        pret.setDateRetourPrevue(dateEmpruntEffective.plusDays(nbJoursPret));
        StatutValidation statutEnAttente = statutValidationRepository.findById(1L).orElse(null);
        pret.setStatutValidation(statutEnAttente); // en attente
        pret.setStatutPret(null); // pas encore "en cours"

        pretRepo.save(pret);

        return pret;
    }

    /**
     * Cherche un exemplaire disponible pour un livre donné.
     */
    private Exemplaire findExemplaireDisponible(Long livreId) {
        StatutDisponibilite statutDispo = statutDisponibiliteRepository.findAll().stream()
                .filter(s -> s.getLibelle().equalsIgnoreCase("disponible"))
                .findFirst().orElseThrow(() -> new IllegalStateException("Statut 'disponible' non trouvé"));
        return exemplaireRepo.findAll().stream()
                .filter(e -> e.getLivre().getId().equals(livreId) && e.getStatut().getId().equals(statutDispo.getId()))
                .findFirst().orElse(null);
    }

    /**
     * Récupère le quota pour un adhérent donné.
     */
    public Quota getQuotaForAdherent(Adherent adherent) {
        Quota quota = quotaRepository.findAll().stream()
                .filter(q -> q.getTypePersonne().getId().equals(adherent.getTypePersonne().getId()))
                .findFirst().orElse(null);
        if (quota == null || quota.getNombreLivrePret() == null) {
            throw new IllegalStateException("Aucun quota défini pour ce type d'adhérent");
        }
        return quota;
    }

    /**
     * Liste des prêts en attente de validation (statut 1)
     */
    public List<Pret> findPretsEnAttente() {
        StatutValidation statutEnAttente = statutValidationRepository.findById(1L).orElse(null);
        return pretRepo.findByStatutValidation(statutEnAttente);
    }

    /**
     * Valider un prêt (passe le statut à 2 = acceptée)
     */
    public void validerPret(Long pretId, Long biblioId) {
        Pret pret = pretRepo.findById(pretId).orElseThrow(() -> new IllegalArgumentException("Prêt introuvable"));
        if (biblioId == null) {
            throw new IllegalStateException("Aucun bibliothécaire connecté pour valider le prêt (biblioId null)");
        }
        Bibliothecaire biblio = bibliothecaireRepository.findById(biblioId)
                .orElseThrow(() -> new IllegalArgumentException("Bibliothécaire introuvable"));

        // Vérification du quota de livres empruntés
        Adherent adherent = pret.getAdherent();
        if (adherent == null || adherent.getTypePersonne() == null) {
            throw new IllegalStateException("Impossible de déterminer le type d'adhérent pour le quota");
        }

        // Vérification de pénalité en cours
        List<Penalite> penalites = penaliteRepository.findByPersonne(adherent);
        LocalDate today = LocalDate.now();

        Penalite penaliteActive = penalites.stream()
                .filter(p -> p.getDateDebut() != null && p.getDateFin() != null &&
                        !today.isBefore(p.getDateDebut()) && !today.isAfter(p.getDateFin()))
                .findFirst().orElse(null);

        if (penaliteActive != null) {
            throw new IllegalStateException("L'adhérent est actuellement sous pénalité du "
                    + penaliteActive.getDateDebut() + " au " + penaliteActive.getDateFin()
                    + " et ne peut pas emprunter.");
        }

        // Récupérer le quota pour le type de personne
        Quota quota = quotaRepository.findAll().stream()
                .filter(q -> q.getTypePersonne().getId().equals(adherent.getTypePersonne().getId()))
                .findFirst().orElse(null);
        if (quota == null || quota.getNombreLivrePret() == null) {
            throw new IllegalStateException("Aucun quota défini pour ce type d'adhérent");
        }

        // Vérification de l'âge minimum pour le livre (restriction)
        RestrictionLivre restriction = restrictionLivreRepository
                .findByLivreId(pret.getExemplaire().getLivre().getId());
        if (restriction != null && restriction.getAgeMinimum() != null) {
            if (adherent.getDateNaissance() == null) {
                throw new IllegalStateException(
                        "Impossible de vérifier l'âge de l'adhérent (date de naissance manquante)");
            }
            int age = java.time.Period.between(adherent.getDateNaissance(), today).getYears();
            if (age < restriction.getAgeMinimum()) {
                throw new IllegalStateException("L'adhérent n'a pas l'âge minimum requis ("
                        + restriction.getAgeMinimum() + " ans) pour emprunter ce livre.");
            }
        }

        // Trouver le statut "rendu" pour l'exclure
        StatutPret statutRendu = statutPretRepository.findByLibelleIgnoreCase("rendu");

        // Compter les prêts en cours (non rendus) de l'adhérent
        int nbPretsEnCours = pretRepo.findByAdherentIdAndStatutPretNot(adherent.getId(), statutRendu).size();
        if (nbPretsEnCours >= quota.getNombreLivrePret()) {
            throw new IllegalStateException("L'adhérent a déjà atteint son quota de livres empruntés en cours");
        }

        // 1. Historiser la validation actuelle : on prend toujours le statut "en cours
        // de validation" (id = 1)
        Optional<StatutValidation> statutEnCours = statutValidationRepository.findById(1L);
        if (statutEnCours.isPresent()) {
            StatutValidationPret historique = new StatutValidationPret();
            historique.setPret(pret);
            historique.setStatutValidation(statutEnCours.get());
            historique.setDateFinStatut(java.time.LocalDateTime.now());
            historique.setBibliothecaire(biblio);
            statutValidationPretRepository.save(historique);
        }

        // 2. Mettre à jour le statut_validation du prêt (2 = acceptée)
        StatutValidation statutValide = statutValidationRepository.findById(2L).orElse(null);
        pret.setStatutValidation(statutValide);

        // 3. Mettre à jour le statut_pret_id à 1 (en cours)
        StatutPret statutPret = statutPretRepository.findAll().stream()
                .filter(sp -> sp.getLibelle() != null && sp.getLibelle().equalsIgnoreCase("en cours"))
                .findFirst().orElse(null);

        // fallback
        pret.setStatutPret(statutPret);

        pretRepo.save(pret);
    }

    /**
     * Refuser un prêt (passe le statut à 3 = refusée)
     */
    public void refuserPret(Long pretId, Long biblioId) {
        Pret pret = pretRepo.findById(pretId).orElseThrow(() -> new IllegalArgumentException("Prêt introuvable"));
        if (biblioId == null) {
            throw new IllegalStateException("Aucun bibliothécaire connecté pour refuser le prêt (biblioId null)");
        }
        Bibliothecaire biblio = bibliothecaireRepository.findById(biblioId)
                .orElseThrow(() -> new IllegalArgumentException("Bibliothécaire introuvable"));

        // 1. Historiser la validation actuelle : on prend toujours le statut "en cours
        // de validation" (id = 1)
        Optional<StatutValidation> statutEnCours = statutValidationRepository.findById(1L);
        if (statutEnCours.isPresent()) {
            StatutValidationPret historique = new StatutValidationPret();
            historique.setPret(pret);
            historique.setStatutValidation(statutEnCours.get());
            historique.setDateFinStatut(java.time.LocalDateTime.now());
            historique.setBibliothecaire(biblio);
            statutValidationPretRepository.save(historique);
        }

        // 2. Mettre à jour le statut_validation du prêt (3 = refusée)
        StatutValidation statutRefuse = statutValidationRepository.findById(3L).orElse(null);
        pret.setStatutValidation(statutRefuse);

        // 3. Laisser statut_pret_id à null (pas de "en cours" pour un refus)
        pret.setStatutPret(null);

        pretRepo.save(pret);
    }

    /**
     * Prêt direct (back office) : effectue un prêt immédiatement, sans validation
     * supplémentaire, en respectant toutes les règles d'éligibilité (quota,
     * pénalités, restrictions, etc.).
     * Ajout de la possibilité de spécifier la date d'emprunt.
     */
    public Pret emprunterLivreDirect(Long adherentId, Long livreId, String typePret, Long biblioId,
            java.time.LocalDateTime dateEmprunt) {

        Adherent adherent = adherentRepo.findById(adherentId)
                .orElseThrow(() -> new IllegalArgumentException("Adhérent introuvable"));
        if (biblioId == null) {
            throw new IllegalStateException(
                    "Aucun bibliothécaire connecté pour effectuer le prêt direct (biblioId null)");
        }
        Bibliothecaire biblio = bibliothecaireRepository.findById(biblioId)
                .orElseThrow(() -> new IllegalArgumentException("Bibliothécaire introuvable"));

        Exemplaire exemplaireDispo = findExemplaireDisponible(livreId);
        Quota quota = null;
        Integer nbJoursPret = null;
        boolean eligible = true;
        String motifRefus = null;
        // Vérification exemplaire disponible
        if (exemplaireDispo == null) {
            eligible = false;
            motifRefus = "Aucun exemplaire disponible pour ce livre";
        }
        // Vérification pénalité
        List<Penalite> penalites = penaliteRepository.findByPersonne(adherent);
        LocalDate today = LocalDate.now();
        Penalite penaliteActive = penalites.stream()
                .filter(p -> p.getDateDebut() != null && p.getDateFin() != null &&
                        !today.isBefore(p.getDateDebut()) && !today.isAfter(p.getDateFin()))
                .findFirst().orElse(null);
        if (eligible && penaliteActive != null) {
            eligible = false;
            motifRefus = "L'adhérent est actuellement sous pénalité du " + penaliteActive.getDateDebut() + " au "
                    + penaliteActive.getDateFin() + " et ne peut pas emprunter.";
        }
        // Vérification quota
        if (eligible) {
            try {
                quota = getQuotaForAdherent(adherent);
                nbJoursPret = quota.getNombreJourPret();
                if (nbJoursPret == null) {
                    eligible = false;
                    motifRefus = "Aucune durée de prêt définie pour ce type d'adhérent";
                }
            } catch (Exception e) {
                eligible = false;
                motifRefus = e.getMessage();
            }
        }
        // Vérification âge minimum
        RestrictionLivre restriction = restrictionLivreRepository.findByLivreId(livreId);
        if (eligible && restriction != null && restriction.getAgeMinimum() != null) {
            if (adherent.getDateNaissance() == null) {
                eligible = false;
                motifRefus = "Impossible de vérifier l'âge de l'adhérent (date de naissance manquante)";
            } else {
                int age = java.time.Period.between(adherent.getDateNaissance(), today).getYears();
                if (age < restriction.getAgeMinimum()) {
                    eligible = false;
                    motifRefus = "L'adhérent n'a pas l'âge minimum requis (" + restriction.getAgeMinimum()
                            + " ans) pour emprunter ce livre.";
                }
            }
        }

        // Vérification quota de livres en cours (seulement pour les prêts "emporte")
        if (eligible && quota != null && "emporte".equalsIgnoreCase(typePret)) {
            StatutPret statutRendu = statutPretRepository.findByLibelleIgnoreCase("rendu");
            int nbPretsEnCours = pretRepo.findByAdherentIdAndStatutPretNot(adherent.getId(), statutRendu).size();
            if (nbPretsEnCours >= quota.getNombreLivrePret()) {
                eligible = false;
                motifRefus = "L'adhérent a déjà atteint son quota de livres empruntés en cours";
            }
        }

        Pret pret = new Pret();
        pret.setAdherent(adherent);
        pret.setExemplaire(exemplaireDispo);
        pret.setTypePret(typePret);
        // Utiliser la date passée ou la date actuelle
        java.time.LocalDateTime dateEmpruntEffective = (dateEmprunt != null) ? dateEmprunt
                : java.time.LocalDateTime.now();
        pret.setDateEmprunt(dateEmpruntEffective);
        if (nbJoursPret != null) {
            pret.setDateRetourPrevue(dateEmpruntEffective.plusDays(nbJoursPret));
        } else {
            pret.setDateRetourPrevue(dateEmpruntEffective);
        }
        String message = null;
        if (eligible) {
            StatutValidation statutValide = statutValidationRepository.findById(2L).orElse(null);
            pret.setStatutValidation(statutValide); // acceptée
            StatutPret statutPret = statutPretRepository.findAll().stream()
                    .filter(sp -> sp.getLibelle() != null && sp.getLibelle().equalsIgnoreCase("en cours"))
                    .findFirst().orElse(null);
            if (statutPret != null) {
                pret.setStatutPret(statutPret);
            } else {
                pret.setStatutPret(null); // fallback
            }
            message = "Le prêt a été validé.";
            pret.setMotifRefus(null);
            pretRepo.save(pret);
            // Historiser la validation
            if (statutValide != null) {
                StatutValidationPret historique = new StatutValidationPret();
                historique.setPret(pret);
                historique.setStatutValidation(statutValide);
                historique.setDateFinStatut(java.time.LocalDateTime.now());
                historique.setBibliothecaire(biblio);
                statutValidationPretRepository.save(historique);
            }
        } else {
            StatutValidation statutRefuse = statutValidationRepository.findById(3L).orElse(null);
            pret.setStatutValidation(statutRefuse); // refusée
            pret.setStatutPret(null);
            message = "Le prêt a été refusé : " + (motifRefus != null ? motifRefus : "Condition non remplie");
            pret.setMotifRefus(motifRefus);
            pretRepo.save(pret);
        }
        // Affichage du message de refus ou succès
        System.out.println(message);
        return pret;
    }

    /**
     * Calcule la vraie date limite de retour en tenant compte des prolongations
     * acceptées, week-ends, jours fériés et configuration.
     */
    public LocalDate getDateLimiteEffective(Pret pret) {
        LocalDate dateRetourPrevue = pret.getDateRetourPrevue().toLocalDate();

        // Vérifier s'il y a des prolongations acceptées pour ce prêt
        List<ProlongationPret> prolongations = prolongationPretRepository.findByPret(pret);

        // Trouver la dernière prolongation acceptée (statut = acceptée, id=2)
        LocalDate dateLimiteFinale = dateRetourPrevue;
        for (ProlongationPret prolongation : prolongations) {
            if (prolongation.getStatut() != null && prolongation.getStatut().getId().equals(2L) &&
                    prolongation.getNouvelleDateRetour() != null) {
                LocalDate nouvelleDateRetour = prolongation.getNouvelleDateRetour().toLocalDate();

                if (nouvelleDateRetour.isAfter(dateLimiteFinale)) {
                    dateLimiteFinale = nouvelleDateRetour;
                }
            }
        }

        // Appliquer la logique des week-ends et jours fériés sur la date finale
        LocalDate date = dateLimiteFinale;

        // Gestion des week-ends
        if (date.getDayOfWeek().getValue() == 6) { // Samedi
            // Vérifier la configuration pour savoir si on va au lundi ou au vendredi
            var configWeekend = configurationRepository.findByNom("weekend_direction");
            if (configWeekend != null && configWeekend.getValeur() != null && configWeekend.getValeur() == -1) {
                // -1 = aller au vendredi précédent
                date = date.minusDays(1);
            } else {
                // Par défaut ou si valeur = 1 : aller au lundi suivant
                date = date.plusDays(2);
            }
        } else if (date.getDayOfWeek().getValue() == 7) { // Dimanche
            var configWeekend = configurationRepository.findByNom("weekend_direction");
            if (configWeekend != null && configWeekend.getValeur() != null && configWeekend.getValeur() == -1) {
                // -1 = aller au vendredi précédent
                date = date.minusDays(2);
            } else {
                // Par défaut ou si valeur = 1 : aller au lundi suivant
                date = date.plusDays(1);
            }
        }

        // Gestion des jours fériés
        boolean isFerie = jourFerieService.isJourFerie(date);
        if (isFerie) {
            int nbJours = 1; // défaut : aller au jour suivant
            var config = configurationRepository.findByNom("alternatif_jour_férié");
            if (config != null && config.getValeur() != null) {
                nbJours = config.getValeur();
            }
            date = date.plusDays(nbJours);

            // Vérifier à nouveau si on tombe sur un week-end après avoir évité le jour
            // férié
            if (date.getDayOfWeek().getValue() == 6) { // Samedi
                var configWeekend = configurationRepository.findByNom("weekend_direction");
                if (configWeekend != null && configWeekend.getValeur() != null && configWeekend.getValeur() == -1) {
                    date = date.minusDays(1); // Vendredi
                } else {
                    date = date.plusDays(2); // Lundi
                }
            } else if (date.getDayOfWeek().getValue() == 7) { // Dimanche
                var configWeekend = configurationRepository.findByNom("weekend_direction");
                if (configWeekend != null && configWeekend.getValeur() != null && configWeekend.getValeur() == -1) {
                    date = date.minusDays(2); // Vendredi
                } else {
                    date = date.plusDays(1); // Lundi
                }
            }
        }

        return date;
    }

    /**
     * Retourne les prêts en cours avec la vraie date limite et l'état du bouton
     * pénaliser.
     */
    public List<PretEnCoursDto> getPretsEnCoursDto() {
        StatutPret statutEnCours = statutPretRepository.findByLibelleIgnoreCase("en cours");
        StatutPret statutProlongement = statutPretRepository.findByLibelleIgnoreCase("en prolongement");

        if (statutEnCours == null && statutProlongement == null)
            return List.of();
        LocalDate now = LocalDate.now();

        return pretRepo.findAll().stream()
                .filter(pret -> {
                    StatutPret sp = pret.getStatutPret();
                    return sp != null && ((statutEnCours != null && sp.getId().equals(statutEnCours.getId())) ||
                            (statutProlongement != null && sp.getId().equals(statutProlongement.getId())));
                })
                .map(pret -> {
                    LocalDate dateLimite = getDateLimiteEffective(pret);
                    boolean isProlongement = pret.getStatutPret() != null && pret.getStatutPret().getLibelle() != null
                            && pret.getStatutPret().getLibelle().equalsIgnoreCase("en prolongement");

                    // Vérifier s'il existe déjà une pénalité pour ce prêt spécifique et cet
                    // adhérent
                    boolean dejaPenalisePourCePret = false;
                    if (pret.getAdherent() != null) {
                        List<mg.itu.library.model.Penalite> penalites = penaliteRepository.findByPret(pret);
                        dejaPenalisePourCePret = penalites.stream().anyMatch(p -> p.getPersonne() != null
                                && p.getPersonne().getId().equals(pret.getAdherent().getId()));
                    }

                    // Le bouton est actif si :
                    // - Il y a un retard (now > dateLimite) - dateLimite inclut maintenant les
                    // prolongations
                    // - Il n'y a pas déjà de pénalité pour ce prêt spécifique
                    // (peu importe le statut "en prolongement" ou "en cours")
                    boolean enRetard = now.isAfter(dateLimite);
                    boolean penaliserActive = enRetard && !dejaPenalisePourCePret;

                    return new PretEnCoursDto(pret, dateLimite, penaliserActive, dejaPenalisePourCePret);
                })
                .toList();
    }

    // Cette méthode a été vérifiée le 07/07/2025
    @Transactional
    public void penaliserPret(Long idPret) {
        Pret pretTrouve = pretRepo.findById(idPret).orElseThrow();
        Adherent adherentTrouve = pretTrouve.getAdherent();
        Quota quotaTrouve = quotaRepository.findByTypePersonneId(adherentTrouve.getTypePersonne().getId());
        int joursPenalite = quotaTrouve != null && quotaTrouve.getNombreJourPenalite() != null
                ? quotaTrouve.getNombreJourPenalite()
                : 5;
        boolean dejaPenalise = penaliteRepository.findByPret(pretTrouve).stream()
                .anyMatch(p -> p.getPersonne() != null && p.getPersonne().getId().equals(adherentTrouve.getId()));
        if (dejaPenalise) {
            throw new IllegalStateException("Cet adhérent a déjà été pénalisé pour ce prêt.");
        }
        penaliteService.penaliserAdherent(adherentTrouve, joursPenalite, pretTrouve);
    }

    // Renommage des méthodes pour obfuscation
    public List<Pret> recupererTousLesPrets() {
        return pretRepo.findAll();
    }

    public List<Pret> obtenirPretsNonRendus(Long idAdh, StatutPret statut) {
        return pretRepo.findByAdherentIdAndStatutPretNot(idAdh, statut);
    }

    public void effectuerRetourPret(Long idPret) {
        rendrePret(idPret);
    }

    // Helper pour générer un message d'état de prêt
    public String genererMessageEtatPret(Pret pret) {
        if (pret.getStatutPret() == null)
            return "Statut inconnu";
        return "Statut du prêt : " + pret.getStatutPret().getLibelle();
    }

    // Ajout d'une méthode utilitaire pour vérifier l'éligibilité à l'emprunt
    public boolean estEligibleEmprunt(Adherent adherent) {
        List<Penalite> penalites = penaliteRepository.findByPersonne(adherent);
        LocalDate today = LocalDate.now();
        return penalites.stream().noneMatch(p -> p.getDateDebut() != null && p.getDateFin() != null &&
                !today.isBefore(p.getDateDebut()) && !today.isAfter(p.getDateFin()));
    }

    // Ajout de la méthode findAll() pour compatibilité avec le contrôleur
    public List<Pret> findAll() {
        return recupererTousLesPrets();
    }

    /**
     * DTO pour la vue des prêts en cours (date limite réelle et état du bouton)
     */
    public static class PretEnCoursDto {
        private Pret pret;
        private LocalDate dateLimite;
        private boolean penaliserActive;
        private boolean adherentPenalise;

        public PretEnCoursDto(Pret pret, LocalDate dateLimite, boolean penaliserActive, boolean adherentPenalise) {
            this.pret = pret;
            this.dateLimite = dateLimite;
            this.penaliserActive = penaliserActive;
            this.adherentPenalise = adherentPenalise;
        }

        public Pret getPret() {
            return pret;
        }

        public LocalDate getDateLimite() {
            return dateLimite;
        }

        public boolean isPenaliserActive() {
            return penaliserActive;
        }

        public boolean isAdherentPenalise() {
            return adherentPenalise;
        }
    }
}
