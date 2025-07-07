package mg.itu.library.controller;

import mg.itu.library.model.StatutProlongation;
import mg.itu.library.service.StatutProlongationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statut-prolongation")
public class StatutProlongationController {
    private final StatutProlongationService service;
    public StatutProlongationController(StatutProlongationService service) { this.service = service; }
    @GetMapping public List<StatutProlongation> getAll() { return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<StatutProlongation> getById(@PathVariable Long id) {
        Optional<StatutProlongation> s = service.findById(id);
        return s.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping public StatutProlongation create(@RequestBody StatutProlongation s) { return service.save(s); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
