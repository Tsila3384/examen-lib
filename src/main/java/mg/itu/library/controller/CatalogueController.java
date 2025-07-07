package mg.itu.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mg.itu.library.model.Livre;
import mg.itu.library.service.LivreService;
import mg.itu.library.service.PretService;

@Controller
public class CatalogueController {
    private final LivreService livreService;
    private final PretService pretService;

    public CatalogueController(LivreService livreService, PretService pretService) {
        this.livreService = livreService;
        this.pretService = pretService;
    }

    // Affiche le catalogue (après login ou redirection)
    @GetMapping("/catalogue")
    public String catalogue(Model model, HttpSession session, @RequestParam(value = "emprunter", required = false) Long livreId) {
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("isLoggedIn", session.getAttribute("adherentId") != null);
        model.addAttribute("livreIdEmprunt", livreId);
        return "accueil";
    }

    // Affiche le choix du type de prêt
    @GetMapping("/catalogue/emprunter")
    public String choisirTypePret(@RequestParam Long livreId, Model model, HttpSession session) {
        if (session.getAttribute("adherentId") == null) {
            // Redirige vers login si non connecté
            return "redirect:/login?redirectTo=/catalogue/emprunter?livreId=" + livreId;
        }
        Livre livre = livreService.findById(livreId);
        model.addAttribute("livre", livre);
        return "choix_type_pret";
    }
}
