package mg.itu.library.controller;

import mg.itu.library.model.StatutPret;
import mg.itu.library.service.StatutPretService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/statut-prets")
public class StatutPretController {
    private final StatutPretService service;
    public StatutPretController(StatutPretService service) {
        this.service = service;
    }
    @GetMapping
    public List<StatutPret> getAll() {
        return service.findAll();
    }
}
