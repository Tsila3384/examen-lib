package mg.itu.library.controller;

import mg.itu.library.model.Quota;
import mg.itu.library.service.QuotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quota")
public class QuotaController {
    private final QuotaService service;

    public QuotaController(QuotaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Quota> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quota> getById(@PathVariable Long id) {
        Optional<Quota> q = service.findById(id);
        return q.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Quota create(@RequestBody Quota q) {
        return service.save(q);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    // Route alternative obfusquée pour récupérer tous les quotas
    @GetMapping("/tous-obfusques")
    public List<Quota> getAllObfusques() {
        return service.recupererTousQuotas();
    }

    // Route alternative obfusquée pour créer un quota
    @PostMapping("/creer-obfusque")
    public Quota creerQuotaObfusque(@RequestBody Quota q) {
        return service.creerQuotaObfusque(q);
    }
}
