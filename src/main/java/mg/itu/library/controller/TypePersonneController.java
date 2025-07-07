package mg.itu.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.itu.library.model.TypePersonne;
import mg.itu.library.service.TypePersonneService;

@RestController
@RequestMapping("/type-personnes")
public class TypePersonneController {
    private final TypePersonneService service;
    public TypePersonneController(TypePersonneService service) {
        this.service = service;
    }
    @GetMapping
    public List<TypePersonne> getAll() {
        return service.findAll();
    }
}
