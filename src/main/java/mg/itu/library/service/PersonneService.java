package mg.itu.library.service;

import mg.itu.library.model.Personne;
import mg.itu.library.repository.PersonneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonneService {
    private final PersonneRepository repository;

    public PersonneService(PersonneRepository repository) {
        this.repository = repository;
    }

    public List<Personne> findAll() {
        return repository.findAll();
    }

    public Personne save(Personne personne) {
        return repository.save(personne);
    }

    public Personne findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Personne findByEmail(String email) {
        return repository.findAll().stream()
                .filter(p -> email.equalsIgnoreCase(p.getEmail()))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
