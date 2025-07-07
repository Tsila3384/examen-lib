package mg.itu.library.controller;

import jakarta.servlet.http.HttpSession;
import mg.itu.library.service.ProlongationPretService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProlongationPretController {
    private final ProlongationPretService service;

    public ProlongationPretController(ProlongationPretService service) {
        this.service = service;
    }

    // --- FRONT OFFICE ---
    // Affiche la liste des prêts éligibles à la prolongation
    @GetMapping("/frontoffice/prolongation")
    public String afficherProlongation(@RequestParam Long adherentId, Model model) {
        model.addAttribute("prets", service.findPretsEligiblesProlongation(adherentId));
        model.addAttribute("adherentId", adherentId);

        return "frontoffice/prolongation";
    }

    // Traite la demande de prolongation
    @PostMapping("/frontoffice/prolongation")
    public String demanderProlongation(@RequestParam Long pretId, @RequestParam Long adherentId, Model model) {
        try {
            String message = service.demanderProlongation(pretId);
            model.addAttribute("prets", service.findPretsEligiblesProlongation(adherentId));
            model.addAttribute("adherentId", adherentId);

            if (message.startsWith("Demande")) {
                model.addAttribute("message", message);
            } else {
                model.addAttribute("erreur", message);
            }
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur technique : " + e.getMessage());
        }

        return "frontoffice/prolongation";
    }

    // --- BACK OFFICE ---
    // Liste des prolongations en attente (backoffice)
    @GetMapping("/backoffice/prolongations/attente")
    public String listeProlongationsAttente(Model model) {
        model.addAttribute("prolongations", service.findProlongationsEnAttente());

        return "backoffice/prolongations_attente";
    }

    // Validation/refus d'une prolongation (backoffice)
    @PostMapping("/backoffice/prolongations/valider")
    public String validerProlongation(@RequestParam Long prolongationId, @RequestParam String action, HttpSession session, Model model) {
        Long biblioId = (Long) session.getAttribute("biblioId");
        if (biblioId == null) {
            model.addAttribute("erreur", "Aucun bibliothécaire connecté. Veuillez vous reconnecter.");
            model.addAttribute("prolongations", service.findProlongationsEnAttente());

            return "backoffice/prolongations_attente";
        }

        String message = service.validerProlongation(prolongationId, action, biblioId);
        model.addAttribute("message", message);
        model.addAttribute("prolongations", service.findProlongationsEnAttente());

        return "backoffice/prolongations_attente";
    }
}
