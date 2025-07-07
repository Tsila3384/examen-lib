package mg.itu.library.controller;

import mg.itu.library.model.Cotisation;
import mg.itu.library.service.CotisationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cotisations")
public class CotisationController {
    private final CotisationService service;
    public CotisationController(CotisationService service) {
        this.service = service;
    }
    @GetMapping
    public List<Cotisation> getAll() {
        return service.findAll();
    }
}
