package mg.itu.library.repository;

import mg.itu.library.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
}
