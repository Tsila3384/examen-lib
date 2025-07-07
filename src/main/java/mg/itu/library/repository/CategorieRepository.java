package mg.itu.library.repository;

import mg.itu.library.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
