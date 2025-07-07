package mg.itu.library.controller;

import mg.itu.library.model.Bibliothecaire;
import mg.itu.library.service.BibliothecaireService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bibliothecaires")
public class BibliothecaireController {
    private final BibliothecaireService service;

    public BibliothecaireController(BibliothecaireService service) {
        this.service = service;
    }

    @GetMapping
    public List<Bibliothecaire> getAll() {
        return service.findAll();
    }
}
