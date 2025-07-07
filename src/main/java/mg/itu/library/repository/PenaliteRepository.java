package mg.itu.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.Penalite;
import mg.itu.library.model.Personne;
import mg.itu.library.model.Pret;

public interface PenaliteRepository extends JpaRepository<Penalite, Long> {
    List<Penalite> findByPersonne(Personne personne);
    List<Penalite> findByPret(Pret pret);
}
