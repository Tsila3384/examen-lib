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
    public String emprunterLivre(@RequestParam Long livreId, @RequestParam String typePret, HttpSession session, Model model) {
        Long adherentId = (Long) session.getAttribute("adherentId");
        if (adherentId == null) {
            return "redirect:/login?redirectTo=/catalogue/emprunter?livreId=" + livreId;
        }
        try {
            Pret pret = pretService.emprunterLivre(adherentId, livreId, typePret);
            model.addAttribute("message", "Emprunt enregistré. En attente de validation.");
        } catch (Exception e) {
            model.addAttribute("erreur", e.getMessage());
        }
        return "redirect:/catalogue";
    }
}
