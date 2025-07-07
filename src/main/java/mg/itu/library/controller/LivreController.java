package mg.itu.library.controller;

import mg.itu.library.model.Livre;
import mg.itu.library.service.LivreService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/livres")
public class LivreController {
    private final LivreService service;
    public LivreController(LivreService service) {
        this.service = service;
    }
    @GetMapping
    public List<Livre> getAll() {
        return service.findAll();
    }
}
