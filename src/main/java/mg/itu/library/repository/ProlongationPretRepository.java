package mg.itu.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.Pret;
import mg.itu.library.model.ProlongationPret;

public interface ProlongationPretRepository extends JpaRepository<ProlongationPret, Long> {
    List<ProlongationPret> findByPret(Pret pret);
}
