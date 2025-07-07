package mg.itu.library.controller;

import mg.itu.library.model.Penalite;
import mg.itu.library.service.PenaliteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/penalites")
public class PenaliteController {
    private final PenaliteService service;
    public PenaliteController(PenaliteService service) {
        this.service = service;
    }
    @GetMapping
    public List<Penalite> getAll() {
        return service.findAll();
    }
    // Route alternative obfusquée pour récupérer toutes les pénalités
    @GetMapping("/toutes-obfusquees")
    public List<Penalite> getAllObfusquees() {
        return service.recupererToutesPenalites();
    }

    // Route alternative obfusquée pour pénaliser un adhérent
    @PostMapping("/penaliser-obfusque")
    public Penalite penaliserObfusque(@RequestBody Penalite penalite) {
        return service.penaliserAdherentObfusque(
            (mg.itu.library.model.Adherent) penalite.getPersonne(),
            (int) java.time.temporal.ChronoUnit.DAYS.between(penalite.getDateDebut(), penalite.getDateFin()),
            penalite.getPret()
        );
    }
}
