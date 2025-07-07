package mg.itu.library.service;

import mg.itu.library.model.Bibliothecaire;
import mg.itu.library.repository.BibliothecaireRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BibliothecaireService {
    private final BibliothecaireRepository repository;
    public BibliothecaireService(BibliothecaireRepository repository) {
        this.repository = repository;
    }
    public List<Bibliothecaire> findAll() {
        return repository.findAll();
    }
}
