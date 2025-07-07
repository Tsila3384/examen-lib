package mg.itu.library.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mg.itu.library.model.Adherent;
import mg.itu.library.model.Exemplaire;
import mg.itu.library.model.Livre;
import mg.itu.library.model.Penalite;
import mg.itu.library.model.Reservation;
import mg.itu.library.service.AdherentService;
import mg.itu.library.service.ExemplaireService;
import mg.itu.library.service.LivreService;
import mg.itu.library.service.PenaliteService;
import mg.itu.library.service.ReservationService;

@Controller

public class FrontOfficeController {
    private final LivreService livreService;
    private final ReservationService reservationService;
    private final AdherentService adherentService;
    private final ExemplaireService exemplaireService;
    private final PenaliteService penaliteService;

    public FrontOfficeController(LivreService livreService, ReservationService reservationService, AdherentService adherentService, ExemplaireService exemplaireService, PenaliteService penaliteService) {
        this.livreService = livreService;
        this.reservationService = reservationService;
        this.adherentService = adherentService;
        this.exemplaireService = exemplaireService;
        this.penaliteService = penaliteService;
    }

    @GetMapping("/frontoffice/emprunt")
    public String showEmpruntForm(Model model, HttpSession session) {
        List<Livre> livres = livreService.findAll();
        model.addAttribute("livres", livres);
        Object adherentId = session.getAttribute("adherentId");
        if (adherentId == null) {
            model.addAttribute("erreur", "Vous devez être connecté comme adhérent pour emprunter un livre.");
        } else {
            model.addAttribute("adherentId", adherentId);
            
            // Vérifier si l'adhérent est actuellement pénalisé
            try {
                Adherent adherent = adherentService.findAll().stream()
                    .filter(a -> a.getId().equals((Long) adherentId))
                    .findFirst().orElse(null);
                    
                if (adherent != null) {
                    List<Penalite> penalites = penaliteService.findByPersonne(adherent);
                    java.time.LocalDate today = java.time.LocalDate.now();
                    
                    Penalite penaliteActive = penalites.stream()
                        .filter(p -> p.getDateDebut() != null && p.getDateFin() != null &&
                                !today.isBefore(p.getDateDebut()) && !today.isAfter(p.getDateFin()))
                        .findFirst().orElse(null);
                        
                    if (penaliteActive != null) {
                        model.addAttribute("adherentPenalise", true);
                        model.addAttribute("penaliteInfo", "Vous êtes actuellement sous pénalité du " 
                            + penaliteActive.getDateDebut() + " au " + penaliteActive.getDateFin() 
                            + ". Vous ne pouvez pas emprunter de livre pendant cette période.");
                    } else {
                        model.addAttribute("adherentPenalise", false);
                    }
                }
            } catch (Exception e) {
                // En cas d'erreur, ne pas bloquer l'affichage
                model.addAttribute("adherentPenalise", false);
            }
        }
        return "frontoffice/emprunt";
    }

    @GetMapping("/frontoffice")
    public String showFrontOffice() {
        return "frontoffice/index";
    }

    @GetMapping("/frontoffice/reservation")
    public String showReservationForm(Model model, HttpSession session) {
        List<Livre> livres = livreService.findAll();
        model.addAttribute("livres", livres);
        Object adherentId = session.getAttribute("adherentId");
        if (adherentId == null) {
            model.addAttribute("erreur", "Vous devez être connecté comme adhérent pour réserver un livre.");
        } else {
            model.addAttribute("adherentId", adherentId);
        }
        return "frontoffice/reservation";
    }

    @PostMapping("/frontoffice/reservation")
    public String submitReservation(@RequestParam("exemplaireId") Long exemplaireId,
                                   @RequestParam("typePret") String typePret,
                                   @RequestParam("dateExpiration") String dateExpirationStr,
                                   HttpSession session, Model model) {
        Object adherentId = session.getAttribute("adherentId");
        if (adherentId == null) {
            model.addAttribute("erreur", "Vous devez être connecté comme adhérent pour réserver un livre.");
            return "frontoffice/reservation";
        }
        try {
            Adherent adherent = adherentService.findAll().stream()
                .filter(a -> a.getId().equals((Long)adherentId)).findFirst().orElse(null);
            Exemplaire exemplaire = exemplaireService.findAll().stream()
                .filter(e -> e.getId().equals(exemplaireId)).findFirst().orElse(null);
            if (adherent == null || exemplaire == null) throw new IllegalArgumentException("Adhérent ou exemplaire introuvable.");
            Reservation reservation = new Reservation();
            reservation.setAdherent(adherent);
            reservation.setExemplaire(exemplaire);
            reservation.setTypePret(typePret);
            // Conversion de la date (format yyyy-MM-dd)
            java.time.LocalDate date = java.time.LocalDate.parse(dateExpirationStr);
            reservation.setDateExpiration(date.atStartOfDay());
            String message = reservationService.reserverLivre(reservation);
            model.addAttribute("message", message);
        } catch (Exception e) {
            model.addAttribute("erreur", e.getMessage());
        }
        List<Livre> livres = livreService.findAll();
        model.addAttribute("livres", livres);
        model.addAttribute("adherentId", adherentId);
        return "frontoffice/reservation";
    }
}
