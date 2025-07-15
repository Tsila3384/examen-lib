package mg.itu.library.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mg.itu.library.model.Adherent;
import mg.itu.library.model.Livre;
import mg.itu.library.model.Pret;
import mg.itu.library.model.Reservation;
import mg.itu.library.service.AdherentService;
import mg.itu.library.service.LivreService;
import mg.itu.library.service.PretService;
import mg.itu.library.service.ReservationService;

@Controller
public class BackofficeController {
    private final PretService pretService;
    private final AdherentService adherentService;
    private final LivreService livreService;
    private final ReservationService reservationService;

    public BackofficeController(PretService pretService, AdherentService adherentService, LivreService livreService,
            ReservationService reservationService) {
        this.pretService = pretService;
        this.adherentService = adherentService;
        this.livreService = livreService;
        this.reservationService = reservationService;
    }

    // Liste des réservations en attente de validation
    @GetMapping("/backoffice/reservations/attente")
    public String reservationsAttente(Model model) {
        List<Reservation> reservations = reservationService.findReservationsEnAttente();
        model.addAttribute("reservations", reservations);
        model.addAttribute("now", java.time.LocalDateTime.now());
        return "backoffice/reservations_attente";
    }

    // Validation ou refus d'une réservation
    @PostMapping("/backoffice/reservations/valider")
    public String validerReservation(@RequestParam("reservationId") Long reservationId,
            @RequestParam("action") String action,
            HttpSession session,
            Model model) {
        Long biblioId = (Long) session.getAttribute("biblioId");
        if (biblioId == null) {
            model.addAttribute("erreur", "Vous devez être connecté comme bibliothécaire.");
            return "redirect:/backoffice/reservations/attente";
        }
        String message = reservationService.validerReservation(reservationId, action, biblioId);
        if (message != null && message.startsWith("Erreur")) {
            model.addAttribute("erreur", message);
        } else {
            model.addAttribute("message", message);
        }
        List<Reservation> reservations = reservationService.findReservationsEnAttente();
        model.addAttribute("reservations", reservations);

        return "backoffice/reservations_attente";
    }

    // Affichage du formulaire de prêt direct
    @GetMapping("/backoffice/pret-direct")
    public String showPretDirectForm(Model model) {
        List<Adherent> adherents = adherentService.findAll();
        List<Livre> livres = livreService.findAll();
        model.addAttribute("adherents", adherents);
        model.addAttribute("livres", livres);
        return "backoffice/pret_direct";
    }

    // Traitement du prêt direct
    @PostMapping("/backoffice/pret-direct")
    public String doPretDirect(@RequestParam Long adherentId, @RequestParam Long livreId, @RequestParam String typePret,
            @RequestParam(required = false) String dateEmprunt, @RequestParam(required = false) String dateRetour,
            HttpSession session, Model model) {
        List<Adherent> adherents = adherentService.findAll();
        List<Livre> livres = livreService.findAll();
        model.addAttribute("adherents", adherents);
        model.addAttribute("livres", livres);
        Long biblioId = (Long) session.getAttribute("biblioId");
        if (biblioId == null) {
            model.addAttribute("erreur", "Aucun bibliothécaire connecté.");
            return "backoffice/pret_direct";
        }
        try {
            java.time.LocalDateTime dateEmpruntValue = null;
            if (dateEmprunt != null && !dateEmprunt.isEmpty()) {
                dateEmpruntValue = java.time.LocalDateTime.parse(dateEmprunt);
            }
            java.time.LocalDateTime dateRetourValue = null;
            if (dateRetour != null && !dateRetour.isEmpty()) {
                dateRetourValue = java.time.LocalDateTime.parse(dateRetour);
            }
            Pret pret = pretService.emprunterLivreDirect(adherentId, livreId, typePret, biblioId, dateEmpruntValue, dateRetourValue);
            if (pret.getStatutValidation() != null && pret.getStatutValidation().getId() == 3) {
                model.addAttribute("erreur", "Le prêt a été refusé : "
                        + (pret.getMotifRefus() != null ? pret.getMotifRefus() : "Condition non remplie"));
            } else {
                model.addAttribute("message", "Prêt enregistré et validé directement.");
            }
        } catch (Exception e) {
            model.addAttribute("erreur", e.getMessage());
        }
        return "backoffice/pret_direct";
    }

    // Liste des prêts en attente de validation
    @GetMapping("/backoffice/prets-attente")
    public String pretsEnAttente(Model model) {
        List<Pret> prets = pretService.findPretsEnAttente();
        model.addAttribute("prets", prets);
        return "backoffice/prets_attente";
    }

    // Validation d'un prêt
    @PostMapping("/backoffice/valider-pret")
    public String validerPret(@RequestParam Long pretId, HttpSession session, Model model) {
        Long biblioId = (Long) session.getAttribute("biblioId");

        try {
            pretService.validerPret(pretId, biblioId);

            return "redirect:/backoffice/prets-attente";
        } catch (IllegalStateException e) {
            // Afficher l'erreur sur la page
            List<Pret> prets = pretService.findPretsEnAttente();
            model.addAttribute("prets", prets);
            model.addAttribute("erreurValidation", e.getMessage());

            return "backoffice/prets_attente";
        }
    }

    // Refus d'un prêt
    @PostMapping("/backoffice/refuser-pret")
    public String refuserPret(@RequestParam Long pretId, HttpSession session) {
        Long biblioId = (Long) session.getAttribute("biblioId");
        pretService.refuserPret(pretId, biblioId);
        return "redirect:/backoffice/prets-attente";
    }

    // Liste des prêts en cours (DTO)
    @GetMapping("/backoffice/prets-en-cours")
    public String pretsEnCours(Model model) {
        List<PretService.PretEnCoursDto> prets = pretService.getPretsEnCoursDto();
        model.addAttribute("pretsEnCours", prets);
        model.addAttribute("now", LocalDate.now());

        return "backoffice/prets_en_cours";
    }

    // Pénaliser un prêt
    @PostMapping("/backoffice/penaliser-pret")
    public String penaliserPret(@RequestParam Long pretId, Model model) {
        try {
            pretService.penaliserPret(pretId);
            model.addAttribute("message", "Pénalité appliquée !");
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la pénalisation : " + e.getMessage());
        }

        return "redirect:/backoffice/prets-en-cours";
    }
}
