package mg.itu.library.repository;

import mg.itu.library.model.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdherentRepository extends JpaRepository<Adherent, Long> {
    Adherent findByEmail(String email);
}
