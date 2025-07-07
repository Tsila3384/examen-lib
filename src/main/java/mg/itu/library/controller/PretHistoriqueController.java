package mg.itu.library.controller;

import mg.itu.library.model.PretHistorique;
import mg.itu.library.service.PretHistoriqueService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pret-historiques")
public class PretHistoriqueController {
    private final PretHistoriqueService service;
    public PretHistoriqueController(PretHistoriqueService service) {
        this.service = service;
    }
    @GetMapping
    public List<PretHistorique> getAll() {
        return service.findAll();
    }
}
