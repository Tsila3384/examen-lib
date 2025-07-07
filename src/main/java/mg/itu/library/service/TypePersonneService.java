package mg.itu.library.service;

import mg.itu.library.model.TypePersonne;
import mg.itu.library.repository.TypePersonneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypePersonneService {
    private final TypePersonneRepository repository;

    public TypePersonneService(TypePersonneRepository repository) {
        this.repository = repository;
    }

    public List<TypePersonne> findAll() {
        return repository.findAll();
    }
}
