package mg.itu.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mg.itu.library.model.StatutExemplaireLivre;
import mg.itu.library.repository.StatutExemplaireLivreRepository;

@Service
public class StatutExemplaireLivreService {
    private final StatutExemplaireLivreRepository repository;
    public StatutExemplaireLivreService(StatutExemplaireLivreRepository repository) {
        this.repository = repository;
    }
    public List<StatutExemplaireLivre> findAll() {
        return repository.findAll();
    }
}
