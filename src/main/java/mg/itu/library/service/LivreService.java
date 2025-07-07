package mg.itu.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mg.itu.library.model.Livre;
import mg.itu.library.repository.LivreRepository;

@Service
public class LivreService {
    private final LivreRepository repository;
    public LivreService(LivreRepository repository) {
        this.repository = repository;
    }
    public List<Livre> findAll() {
        return repository.findAll();
    }

    public Livre findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
