package mg.itu.library.service;

import mg.itu.library.model.PretHistorique;
import mg.itu.library.repository.PretHistoriqueRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PretHistoriqueService {
    private final PretHistoriqueRepository repository;
    public PretHistoriqueService(PretHistoriqueRepository repository) {
        this.repository = repository;
    }
    public List<PretHistorique> findAll() {
        return repository.findAll();
    }
}
