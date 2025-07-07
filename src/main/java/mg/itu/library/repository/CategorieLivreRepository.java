package mg.itu.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.CategorieLivre;
import mg.itu.library.model.CategorieLivre.CategorieLivreId;

public interface CategorieLivreRepository extends JpaRepository<CategorieLivre, CategorieLivreId> {
}
