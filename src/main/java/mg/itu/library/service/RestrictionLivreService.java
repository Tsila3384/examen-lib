package mg.itu.library.service;

import mg.itu.library.model.RestrictionLivre;
import mg.itu.library.repository.RestrictionLivreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestrictionLivreService {
    private final RestrictionLivreRepository repository;
    public RestrictionLivreService(RestrictionLivreRepository repository) {
        this.repository = repository;
    }
    public List<RestrictionLivre> findAll() {
        return repository.findAll();
    }
}
