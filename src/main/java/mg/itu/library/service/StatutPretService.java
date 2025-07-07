package mg.itu.library.service;

import mg.itu.library.model.StatutPret;
import mg.itu.library.repository.StatutPretRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StatutPretService {
    private final StatutPretRepository repository;
    public StatutPretService(StatutPretRepository repository) {
        this.repository = repository;
    }
    public List<StatutPret> findAll() {
        return repository.findAll();
    }
}
