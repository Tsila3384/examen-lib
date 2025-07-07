package mg.itu.library.service;

import mg.itu.library.model.StatutProlongation;
import mg.itu.library.repository.StatutProlongationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatutProlongationService {
    private final StatutProlongationRepository repository;
    public StatutProlongationService(StatutProlongationRepository repository) { this.repository = repository; }
    public List<StatutProlongation> findAll() { return repository.findAll(); }
    public Optional<StatutProlongation> findById(Long id) { return repository.findById(id); }
    public StatutProlongation save(StatutProlongation s) { return repository.save(s); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
