package mg.itu.library.service;

import mg.itu.library.model.HistoriqueStatutAdherent;
import mg.itu.library.repository.HistoriqueStatutAdherentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoriqueStatutAdherentService {
    private final HistoriqueStatutAdherentRepository repository;
    public HistoriqueStatutAdherentService(HistoriqueStatutAdherentRepository repository) {
        this.repository = repository;
    }
    public List<HistoriqueStatutAdherent> findAll() {
        return repository.findAll();
    }
}
