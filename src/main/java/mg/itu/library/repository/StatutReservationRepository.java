package mg.itu.library.repository;

import mg.itu.library.model.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutReservationRepository extends JpaRepository<StatutReservation, Long> {
}
