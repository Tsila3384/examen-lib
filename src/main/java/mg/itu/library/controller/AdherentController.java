package mg.itu.library.controller;

import mg.itu.library.dto.AdherentInscriptionRequest;
import mg.itu.library.model.Adherent;
import mg.itu.library.service.AdherentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adherents")
public class AdherentController {
    private final AdherentService service;

    public AdherentController(AdherentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Adherent> getAll() {
        return service.findAll();
    }

    @PostMapping("/inscription")
    public ResponseEntity<?> inscrire(@RequestBody AdherentInscriptionRequest req) {
        try {
            Adherent adherent = service.inscrireNouvelAdherent(req);
            return ResponseEntity.ok(adherent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
