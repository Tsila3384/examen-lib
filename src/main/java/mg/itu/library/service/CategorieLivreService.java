package mg.itu.library.service;

import mg.itu.library.model.CategorieLivre;
import mg.itu.library.model.CategorieLivre.CategorieLivreId;
import mg.itu.library.repository.CategorieLivreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieLivreService {
    private final CategorieLivreRepository categorieLivreRepository;

    public CategorieLivreService(CategorieLivreRepository categorieLivreRepository) {
        this.categorieLivreRepository = categorieLivreRepository;
    }

    public List<CategorieLivre> findAll() {
        return categorieLivreRepository.findAll();
    }

    public Optional<CategorieLivre> findById(CategorieLivreId id) {
        return categorieLivreRepository.findById(id);
    }

    public CategorieLivre save(CategorieLivre categorieLivre) {
        return categorieLivreRepository.save(categorieLivre);
    }

    public void deleteById(CategorieLivreId id) {
        categorieLivreRepository.deleteById(id);
    }
}
