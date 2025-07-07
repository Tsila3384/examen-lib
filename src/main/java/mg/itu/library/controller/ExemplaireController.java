package mg.itu.library.controller;

import mg.itu.library.model.Exemplaire;
import mg.itu.library.service.ExemplaireService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/exemplaires")
public class ExemplaireController {
    private final ExemplaireService service;
    public ExemplaireController(ExemplaireService service) {
        this.service = service;
    }
    @GetMapping
    public List<Exemplaire> getAll() {
        return service.findAll();
    }
}
