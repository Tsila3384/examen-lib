package mg.itu.library.controller;

import mg.itu.library.model.HistoriqueStatutAdherent;
import mg.itu.library.service.HistoriqueStatutAdherentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/historique-statut-adherents")
public class HistoriqueStatutAdherentController {
    private final HistoriqueStatutAdherentService service;
    public HistoriqueStatutAdherentController(HistoriqueStatutAdherentService service) {
        this.service = service;
    }
    @GetMapping
    public List<HistoriqueStatutAdherent> getAll() {
        return service.findAll();
    }
}
