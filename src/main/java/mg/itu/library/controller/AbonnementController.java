package mg.itu.library.controller;

import mg.itu.library.model.Abonnement;
import mg.itu.library.service.AbonnementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/abonnement")
public class AbonnementController {
    private final AbonnementService service;
    public AbonnementController(AbonnementService service) { this.service = service; }
    @GetMapping public List<Abonnement> getAll() { return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Abonnement> getById(@PathVariable Long id) {
        Optional<Abonnement> a = service.findById(id);
        return a.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping public Abonnement create(@RequestBody Abonnement a) { return service.save(a); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
