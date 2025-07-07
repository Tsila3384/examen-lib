package mg.itu.library.repository;

import mg.itu.library.model.Cotisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotisationRepository extends JpaRepository<Cotisation, Long> {
}
