package mg.itu.library.repository;

import mg.itu.library.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepository extends JpaRepository<Livre, Long> {
}
