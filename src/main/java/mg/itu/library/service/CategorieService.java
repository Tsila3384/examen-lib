package mg.itu.library.service;

import mg.itu.library.model.Categorie;
import mg.itu.library.repository.CategorieRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategorieService {
    private final CategorieRepository repository;
    public CategorieService(CategorieRepository repository) {
        this.repository = repository;
    }
    public List<Categorie> findAll() {
        return repository.findAll();
    }
}
