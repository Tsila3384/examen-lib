package mg.itu.library.controller;

import mg.itu.library.model.StatutExemplaireLivre;
import mg.itu.library.service.StatutExemplaireLivreService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/statut-exemplaire-livres")
public class StatutExemplaireLivreController {
    private final StatutExemplaireLivreService service;
    public StatutExemplaireLivreController(StatutExemplaireLivreService service) {
        this.service = service;
    }
    @GetMapping
    public List<StatutExemplaireLivre> getAll() {
        return service.findAll();
    }
}
