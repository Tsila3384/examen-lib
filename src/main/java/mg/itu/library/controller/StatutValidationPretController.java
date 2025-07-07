package mg.itu.library.controller;

import mg.itu.library.model.StatutValidationPret;
import mg.itu.library.service.StatutValidationPretService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statut-validation-pret")
public class StatutValidationPretController {
    private final StatutValidationPretService service;
    public StatutValidationPretController(StatutValidationPretService service) { this.service = service; }
    @GetMapping public List<StatutValidationPret> getAll() { return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<StatutValidationPret> getById(@PathVariable Long id) {
        Optional<StatutValidationPret> s = service.findById(id);
        return s.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping public StatutValidationPret create(@RequestBody StatutValidationPret s) { return service.save(s); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
