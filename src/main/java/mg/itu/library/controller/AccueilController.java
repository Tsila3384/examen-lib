package mg.itu.library.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import mg.itu.library.model.Livre;
import mg.itu.library.service.LivreService;

@Controller
public class AccueilController {
    private final LivreService livreService;

    public AccueilController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping("/")
    public String accueil(Model model, HttpSession session) {
        List<Livre> livres = livreService.findAll();
        model.addAttribute("livres", livres);
        // Pour savoir si l'utilisateur est connecté
        Object adherentId = session.getAttribute("adherentId");
        model.addAttribute("isLoggedIn", adherentId != null);
        return "accueil";
    }
}
