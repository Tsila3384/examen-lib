package mg.itu.library.controller;

import mg.itu.library.model.Personne;
import mg.itu.library.service.PersonneService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/personnes")
public class PersonneController {
    private final PersonneService service;
    public PersonneController(PersonneService service) {
        this.service = service;
    }
    @GetMapping
    public List<Personne> getAll() {
        return service.findAll();
    }
}
