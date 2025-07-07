package mg.itu.library.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mg.itu.library.model.Bibliothecaire;
import mg.itu.library.model.Penalite;
import mg.itu.library.model.Pret;
import mg.itu.library.model.Quota;
import mg.itu.library.model.Reservation;
import mg.itu.library.model.StatutReservation;
import mg.itu.library.model.StatutValidation;
import mg.itu.library.repository.BibliothecaireRepository;
import mg.itu.library.repository.PenaliteRepository;
import mg.itu.library.repository.PretRepository;
import mg.itu.library.repository.ReservationRepository;
import mg.itu.library.repository.StatutReservationRepository;
import mg.itu.library.repository.StatutValidationRepository;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final StatutValidationRepository statutValidationRepository;
    private final StatutReservationRepository statutReservationRepository;
    private final BibliothecaireRepository bibliothecaireRepository;
    private final PretService pretService;
    private final PretRepository pretRepository;
    private final PenaliteRepository penaliteRepository;
    private final AbonnementService abonnementService;

    public ReservationService(ReservationRepository repository,
                              StatutValidationRepository statutValidationRepository,
                              StatutReservationRepository statutReservationRepository,
                              BibliothecaireRepository bibliothecaireRepository,
                              PretService pretService, PretRepository pretRepository,
                              PenaliteRepository penaliteRepository, AbonnementService abonnementService) {
        this.repository = repository;
        this.statutValidationRepository = statutValidationRepository;
        this.statutReservationRepository = statutReservationRepository;
        this.bibliothecaireRepository = bibliothecaireRepository;
        this.pretService = pretService;
        this.pretRepository = pretRepository;
        this.penaliteRepository = penaliteRepository;
        this.abonnementService = abonnementService;
    }

    public List<Reservation> findAll() {
        return repository.findAll();
    }

    // Création d'une réservation (front office)
    @Transactional
    public String reserverLivre(Reservation reservation) {
        // Vérification pénalité en cours
        List<Penalite> penalites = penaliteRepository.findByPersonne(reservation.getAdherent());
        java.time.LocalDate today = java.time.LocalDate.now();
        Penalite penaliteActive = penalites.stream()
            .filter(p -> p.getDateDebut() != null && p.getDateFin() != null &&
                !today.isBefore(p.getDateDebut()) && !today.isAfter(p.getDateFin()))
            .findFirst().orElse(null);
        if (penaliteActive != null) {
            return "Vous êtes actuellement sous pénalité du "
                + penaliteActive.getDateDebut() + " au " + penaliteActive.getDateFin()
                + " et ne pouvez pas réserver de livre.";
        }
        // Vérification abonnement actif
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        if (!abonnementService.hasAbonnementActif(reservation.getAdherent(), now)) {
            return "Vous n'avez pas d'abonnement actif à la date de la réservation. Veuillez renouveler votre abonnement.";
        }
        StatutValidation statutEnCours = statutValidationRepository.findById(1L).orElse(null); // en cours de validation
        if (statutEnCours == null)
            return "Erreur technique : statut de validation introuvable.";
        reservation.setStatut(statutEnCours);
        reservation.setDateReservation(now);
        // La dateExpiration est déjà fixée par le contrôleur (date de besoin)
        repository.save(reservation);
        return "Réservation enregistrée (en cours de validation).";
    }

    // Validation, refus ou prévision de prêt (back office)
    @Transactional
    public String validerReservation(Long reservationId, String action, Long biblioId) {
        Reservation reservation = repository.findById(reservationId).orElse(null);
        if (reservation == null)
            return "Réservation introuvable.";
        StatutValidation oldStatut = reservation.getStatut();
        LocalDateTime now = LocalDateTime.now();
        StatutValidation statut = null;
        String message = "";
        Bibliothecaire biblio = bibliothecaireRepository.findById(biblioId).orElse(null);
        if (biblio == null)
            throw new IllegalStateException("Aucun bibliothécaire trouvé pour l'action.");

        if ("accepter".equals(action)) {
            statut = statutValidationRepository.findById(2L).orElse(null); // acceptée
            // Historiser l'ancien statut
            StatutReservation historique = new StatutReservation();
            historique.setReservation(reservation);
            historique.setStatut(oldStatut);
            historique.setBibliothecaire(biblio);
            historique.setDateFin(now);
            statutReservationRepository.save(historique);
            reservation.setStatut(statut);
            repository.save(reservation);
            message = "Réservation acceptée.";
        } else if ("refuser".equals(action)) {
            statut = statutValidationRepository.findById(3L).orElse(null); // refusée
            // Historiser l'ancien statut
            StatutReservation historique = new StatutReservation();
            historique.setReservation(reservation);
            historique.setStatut(oldStatut);
            historique.setBibliothecaire(biblio);
            historique.setDateFin(now);
            statutReservationRepository.save(historique);
            reservation.setStatut(statut);
            repository.save(reservation);
            message = "Réservation refusée.";
        } else if ("prevoir".equals(action)) {
            // Historiser l'ancien statut (acceptée)
            StatutReservation historique = new StatutReservation();
            historique.setReservation(reservation);
            historique.setStatut(oldStatut);
            historique.setBibliothecaire(biblio);
            historique.setDateFin(now);
            statutReservationRepository.save(historique);
            // Mettre à jour le statut de la réservation à "prévu"
            StatutValidation statutPrevu = statutValidationRepository.findById(4L).orElse(null); // prévu
            if (statutPrevu == null) {
                message = "Erreur : le statut 'prévu' (id=4) n'existe pas dans la table statut_validation.";
                throw new IllegalStateException(message);
            }
            reservation.setStatut(statutPrevu);
            repository.save(reservation);

            Quota quota = pretService.getQuotaForAdherent(reservation.getAdherent());
            Integer nbJoursPret = quota.getNombreJourPret();
            if (nbJoursPret == null) {
                throw new IllegalStateException("Aucune durée de prêt définie pour ce type d'adhérent");
            }

            // Création automatique du prêt associé
            Pret pret = new Pret();
            pret.setAdherent(reservation.getAdherent());
            pret.setExemplaire(reservation.getExemplaire());
            pret.setTypePret(reservation.getTypePret());
            // Date d'emprunt = date de besoin (dateExpiration de la réservation)
            pret.setDateEmprunt(reservation.getDateExpiration());
            pret.setDateRetourPrevue(reservation.getDateExpiration().plusDays(nbJoursPret));
            // Le prêt doit être en statut "en cours de validation" (id=1)
            StatutValidation statutPretEnCours = statutValidationRepository.findById(1L).orElse(null); // en cours de
            // validation
            if (statutPretEnCours == null) {
                message = "Erreur : le statut 'en cours de validation' (id=1) n'existe pas dans la table statut_validation.";
                throw new IllegalStateException(message);
            }
            pret.setStatutValidation(statutPretEnCours);

            pretRepository.save(pret);
        }

        return message;
    }

    // Annulation (back office ou front office)
    @Transactional
    public String annulerReservation(Long reservationId, Long biblioId) {
        Reservation reservation = repository.findById(reservationId).orElse(null);
        if (reservation == null)
            return "Réservation introuvable.";

        StatutValidation oldStatut = reservation.getStatut();
        StatutValidation statutRefuse = statutValidationRepository.findById(3L).orElse(null); // refusée
        LocalDateTime now = LocalDateTime.now();
        // Historiser
        StatutReservation historique = new StatutReservation();
        historique.setReservation(reservation);
        historique.setStatut(oldStatut);

        Bibliothecaire biblio = bibliothecaireRepository.findById(biblioId).orElse(null);
        if (biblio == null)
            throw new IllegalStateException("Aucun bibliothécaire trouvé pour l'action.");
        historique.setBibliothecaire(biblio);
        historique.setDateFin(now);
        statutReservationRepository.save(historique);
        // Mettre à jour le statut de la réservation
        reservation.setStatut(statutRefuse);
        repository.save(reservation);

        return "Réservation annulée.";
    }

    // Liste des réservations à afficher (ni refusées, ni prévues, ni annulées)
    public List<Reservation> findReservationsEnAttente() {
        StatutValidation statutEnCours = statutValidationRepository.findById(1L).orElse(null);
        StatutValidation statutAcceptee = statutValidationRepository.findById(2L).orElse(null);
        StatutValidation statutPrevu = statutValidationRepository.findById(4L).orElse(null);
        return repository.findAll().stream()
            .filter(r -> r.getStatut() != null &&
                (r.getStatut().equals(statutEnCours) || r.getStatut().equals(statutAcceptee))
                && !r.getStatut().equals(statutPrevu))
            .toList();
    }

    // Méthode obfusquée pour récupérer toutes les réservations
    public List<Reservation> recupererToutesReservations() {
        return repository.findAll();
    }

    // Méthode utilitaire pour vérifier si un adhérent peut réserver
    public boolean peutReserver(Long adherentId) {
        // On considère qu'un adhérent ne peut réserver que s'il n'a pas de pénalité active
        List<Penalite> penalites = penaliteRepository.findByPersonne(
            bibliothecaireRepository.findById(adherentId).orElse(null));
        java.time.LocalDate today = java.time.LocalDate.now();
        return penalites.stream().noneMatch(p -> p.getDateDebut() != null && p.getDateFin() != null &&
                !today.isBefore(p.getDateDebut()) && !today.isAfter(p.getDateFin()));
    }

    // Méthode obfusquée pour créer une réservation
    public String creerReservationObfusquee(Reservation reservation) {
        // ...logique personnalisée ou modifiée...
        return reserverLivre(reservation);
    }
}
