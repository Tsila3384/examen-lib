package mg.itu.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mg.itu.library.model.Adherent;
import mg.itu.library.model.Bibliothecaire;
import mg.itu.library.repository.AdherentRepository;
import mg.itu.library.repository.BibliothecaireRepository;

@Controller
public class LoginController {
    private final AdherentRepository adherentRepository;
    private final BibliothecaireRepository bibliothecaireRepository;

    public LoginController(AdherentRepository adherentRepository, BibliothecaireRepository bibliothecaireRepository) {
        this.adherentRepository = adherentRepository;
        this.bibliothecaireRepository = bibliothecaireRepository;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // Pour le développement : fournir la liste des emails et mots de passe
        model.addAttribute("adherents", adherentRepository.findAll());
        model.addAttribute("bibliothecaires", bibliothecaireRepository.findAll());

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String motDePasse, Model model, HttpSession session) {
        Bibliothecaire biblio = bibliothecaireRepository.findByEmail(email);

        if (biblio != null && biblio.getMotDePasse().equals(motDePasse)) {
            // Stocker l'id du bibliothécaire en session pour le backoffice
            session.setAttribute("biblioId", biblio.getId());

            return "redirect:/backoffice";
        }

        Adherent adherent = adherentRepository.findByEmail(email);
        if (adherent != null && adherent.getMotDePasse().equals(motDePasse)) {
            // Stocker l'id adhérent en session
            session.setAttribute("adherentId", adherent.getId());

            return "redirect:/frontoffice";
        }

        model.addAttribute("message", "Email ou mot de passe incorrect");
        return "login";
    }
}
