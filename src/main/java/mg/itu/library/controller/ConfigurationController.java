package mg.itu.library.controller;

import mg.itu.library.model.Configuration;
import mg.itu.library.service.ConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {
    private final ConfigurationService service;
    public ConfigurationController(ConfigurationService service) { this.service = service; }
    @GetMapping public List<Configuration> getAll() { return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Configuration> getById(@PathVariable Long id) {
        Optional<Configuration> s = service.findById(id);
        return s.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping public Configuration create(@RequestBody Configuration s) { return service.save(s); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
