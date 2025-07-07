package mg.itu.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.RestrictionLivre;

public interface RestrictionLivreRepository extends JpaRepository<RestrictionLivre, Long> {
    RestrictionLivre findByLivreId(Long livreId);
}
