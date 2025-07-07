package mg.itu.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.StatutPret;

public interface StatutPretRepository extends JpaRepository<StatutPret, Long> {
    StatutPret findByLibelleIgnoreCase(String labelle);
}
