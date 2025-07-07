package mg.itu.library.service;

import mg.itu.library.model.StatutReservation;
import mg.itu.library.repository.StatutReservationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StatutReservationService {
    private final StatutReservationRepository repository;
    public StatutReservationService(StatutReservationRepository repository) {
        this.repository = repository;
    }
    public List<StatutReservation> findAll() {
        return repository.findAll();
    }
}
