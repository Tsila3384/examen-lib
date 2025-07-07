package mg.itu.library.controller;

import mg.itu.library.model.Categorie;
import mg.itu.library.service.CategorieService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategorieController {
    private final CategorieService service;
    public CategorieController(CategorieService service) {
        this.service = service;
    }
    @GetMapping
    public List<Categorie> getAll() {
        return service.findAll();
    }
}
