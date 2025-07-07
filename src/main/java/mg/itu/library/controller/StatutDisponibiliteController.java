package mg.itu.library.controller;

import mg.itu.library.model.StatutDisponibilite;
import mg.itu.library.service.StatutDisponibiliteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/statut-disponibilite")
public class StatutDisponibiliteController {
    private final StatutDisponibiliteService service;
    public StatutDisponibiliteController(StatutDisponibiliteService service) {
        this.service = service;
    }
    @GetMapping
    public String list(Model model) {
        List<StatutDisponibilite> statuts = service.findAll();
        model.addAttribute("statuts", statuts);
        return "statut_disponibilite/list";
    }
    @PostMapping
    public String add(@ModelAttribute StatutDisponibilite statut) {
        service.save(statut);
        return "redirect:/statut-disponibilite";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/statut-disponibilite";
    }
}
