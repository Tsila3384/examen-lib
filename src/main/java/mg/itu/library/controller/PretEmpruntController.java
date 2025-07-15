package mg.itu.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mg.itu.library.model.Pret;
import mg.itu.library.service.PretService;

@Controller
public class PretEmpruntController {
    private final PretService pretService;

    public PretEmpruntController(PretService pretService) {
        this.pretService = pretService;
    }

    @PostMapping("/prets/emprunter")
    public String emprunterLivre(@RequestParam Long livreId, @RequestParam String typePret,
            @RequestParam(required = false) String dateEmprunt,  @RequestParam(required = false) String dateRetour,
            HttpSession session, Model model) {
        Long adherentId = (Long) session.getAttribute("adherentId");
        if (adherentId == null) {
            return "redirect:/login?redirectTo=/catalogue/emprunter?livreId=" + livreId;
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
            Pret pret = pretService.emprunterLivre(adherentId, livreId, typePret, dateEmpruntValue, dateRetourValue);
            model.addAttribute("message", "Emprunt enregistré. En attente de validation.");
        } catch (Exception e) {
            model.addAttribute("erreur", e.getMessage());
        }
        return "redirect:/frontoffice";
    }
}
