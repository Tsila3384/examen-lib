package mg.itu.library.controller;

import mg.itu.library.model.StatutValidation;
import mg.itu.library.service.StatutValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statut-validation")
public class StatutValidationController {
    private final StatutValidationService service;
    public StatutValidationController(StatutValidationService service) { this.service = service; }
    @GetMapping public List<StatutValidation> getAll() { return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<StatutValidation> getById(@PathVariable Long id) {
        Optional<StatutValidation> s = service.findById(id);
        return s.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping public StatutValidation create(@RequestBody StatutValidation s) { return service.save(s); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
