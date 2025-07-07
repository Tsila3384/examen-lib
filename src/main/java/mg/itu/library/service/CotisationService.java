package mg.itu.library.service;

import mg.itu.library.model.Cotisation;
import mg.itu.library.repository.CotisationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CotisationService {
    private final CotisationRepository repository;
    public CotisationService(CotisationRepository repository) {
        this.repository = repository;
    }
    public List<Cotisation> findAll() {
        return repository.findAll();
    }
}
