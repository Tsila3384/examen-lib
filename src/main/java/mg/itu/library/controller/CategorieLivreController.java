package mg.itu.library.controller;

import mg.itu.library.model.CategorieLivre;
import mg.itu.library.model.CategorieLivre.CategorieLivreId;
import mg.itu.library.service.CategorieLivreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorie-livre")
public class CategorieLivreController {
    private final CategorieLivreService categorieLivreService;

    public CategorieLivreController(CategorieLivreService categorieLivreService) {
        this.categorieLivreService = categorieLivreService;
    }

    @GetMapping
    public List<CategorieLivre> getAll() {
        return categorieLivreService.findAll();
    }

    @GetMapping("/{livreId}/{categorieId}")
    public ResponseEntity<CategorieLivre> getById(@PathVariable Long livreId, @PathVariable Long categorieId) {
        CategorieLivreId id = new CategorieLivreId(livreId, categorieId);
        Optional<CategorieLivre> result = categorieLivreService.findById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CategorieLivre create(@RequestBody CategorieLivre categorieLivre) {
        return categorieLivreService.save(categorieLivre);
    }

    @DeleteMapping("/{livreId}/{categorieId}")
    public void delete(@PathVariable Long livreId, @PathVariable Long categorieId) {
        CategorieLivreId id = new CategorieLivreId(livreId, categorieId);
        categorieLivreService.deleteById(id);
    }
}
