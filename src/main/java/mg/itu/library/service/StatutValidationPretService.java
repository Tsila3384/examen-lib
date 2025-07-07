package mg.itu.library.service;

import mg.itu.library.model.StatutValidationPret;
import mg.itu.library.repository.StatutValidationPretRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatutValidationPretService {
    private final StatutValidationPretRepository repository;

    public StatutValidationPretService(StatutValidationPretRepository repository) {
        this.repository = repository;
    }

    public List<StatutValidationPret> findAll() {
        return repository.findAll();
    }

    public Optional<StatutValidationPret> findById(Long id) {
        return repository.findById(id);
    }

    public StatutValidationPret save(StatutValidationPret s) {
        return repository.save(s);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
