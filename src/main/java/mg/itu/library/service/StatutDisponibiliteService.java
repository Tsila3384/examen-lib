package mg.itu.library.service;

import mg.itu.library.model.StatutDisponibilite;
import mg.itu.library.repository.StatutDisponibiliteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatutDisponibiliteService {
    private final StatutDisponibiliteRepository repository;
    public StatutDisponibiliteService(StatutDisponibiliteRepository repository) {
        this.repository = repository;
    }
    public List<StatutDisponibilite> findAll() { return repository.findAll(); }
    public StatutDisponibilite save(StatutDisponibilite s) { return repository.save(s); }
    public void deleteById(Long id) { repository.deleteById(id); }
    public StatutDisponibilite findById(Long id) { return repository.findById(id).orElse(null); }
}
