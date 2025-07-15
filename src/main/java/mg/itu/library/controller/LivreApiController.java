package mg.itu.library.controller;

import mg.itu.library.model.Livre;
import mg.itu.library.model.Exemplaire;
import mg.itu.library.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/api/livres")
public class LivreApiController {
    @Autowired
    private LivreRepository livreRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getLivreWithExemplaires(@PathVariable Long id) {
        Optional<Livre> livreOpt = livreRepository.findById(id);
        if (livreOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Livre livre = livreOpt.get();
        // Les exemplaires sont chargés via la relation OneToMany
        
        List<Map<String, Object>> exemplaires = new ArrayList<>();
        for (Exemplaire ex : livre.getExemplaires()) {
            Map<String, Object> exJson = new HashMap<>();
            exJson.put("id", ex.getId());
            exJson.put("statut", ex.getStatut() != null ? ex.getStatut().getLibelle() : null);
            exemplaires.add(exJson);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", livre.getId());
        result.put("reference", livre.getReference());
        result.put("titre", livre.getTitre());
        result.put("auteur", livre.getAuteur());
        result.put("resume", livre.getResume());
        result.put("exemplaires", exemplaires);
        return ResponseEntity.ok(result);
    }
}
