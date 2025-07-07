package mg.itu.library.controller;

import mg.itu.library.model.RestrictionLivre;
import mg.itu.library.service.RestrictionLivreService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/restriction-livres")
public class RestrictionLivreController {
    private final RestrictionLivreService service;
    public RestrictionLivreController(RestrictionLivreService service) {
        this.service = service;
    }
    @GetMapping
    public List<RestrictionLivre> getAll() {
        return service.findAll();
    }
}
