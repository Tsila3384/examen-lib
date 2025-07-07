package mg.itu.library.controller;

import mg.itu.library.model.JourFerie;
import mg.itu.library.service.JourFerieService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jours-feries")
public class JourFerieController {
    private final JourFerieService service;
    public JourFerieController(JourFerieService service) {
        this.service = service;
    }
    @GetMapping
    public List<JourFerie> getAll() {
        return service.findAll();
    }
}
