package mg.itu.library.controller;

import mg.itu.library.dto.AdherentInscriptionRequest;
import mg.itu.library.model.TypePersonne;
import mg.itu.library.service.AdherentService;
import mg.itu.library.service.TypePersonneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdherentMvcController {
    private final TypePersonneService typePersonneService;
    private final AdherentService adherentService;

    public AdherentMvcController(TypePersonneService typePersonneService, AdherentService adherentService) {
        this.typePersonneService = typePersonneService;
        this.adherentService = adherentService;
    }

    @GetMapping("/inscription-adherent")
    public String showInscriptionForm(Model model) {
        List<TypePersonne> types = typePersonneService.findAll();

        model.addAttribute("typesPersonne", types);
        model.addAttribute("adherentRequest", new AdherentInscriptionRequest());

        return "inscription_adherent";
    }

    @PostMapping("/inscription-adherent")
    public String inscrireAdherent(@ModelAttribute("adherentRequest") AdherentInscriptionRequest req, Model model) {
        try {
            adherentService.inscrireNouvelAdherent(req);
            model.addAttribute("message", "Inscription réussie !");
        } catch (Exception e) {
            model.addAttribute("message", "Erreur : " + e.getMessage());
        }
        model.addAttribute("typesPersonne", typePersonneService.findAll());

        return "inscription_adherent";
    }
}
