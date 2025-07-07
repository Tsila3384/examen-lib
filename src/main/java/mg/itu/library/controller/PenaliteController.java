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
}
