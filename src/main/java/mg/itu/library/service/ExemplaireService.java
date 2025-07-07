package mg.itu.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mg.itu.library.model.Exemplaire;
import mg.itu.library.repository.ExemplaireRepository;

@Service
public class ExemplaireService {
    private final ExemplaireRepository repository;
    public ExemplaireService(ExemplaireRepository repository) {
        this.repository = repository;
    }
    public List<Exemplaire> findAll() {
        return repository.findAll();
    }
}
