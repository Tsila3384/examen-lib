package mg.itu.library.service;

import mg.itu.library.model.StatutValidation;
import mg.itu.library.repository.StatutValidationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatutValidationService {
    private final StatutValidationRepository repository;
    public StatutValidationService(StatutValidationRepository repository) { this.repository = repository; }
    public List<StatutValidation> findAll() { return repository.findAll(); }
    public Optional<StatutValidation> findById(Long id) { return repository.findById(id); }
    public StatutValidation save(StatutValidation s) { return repository.save(s); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
