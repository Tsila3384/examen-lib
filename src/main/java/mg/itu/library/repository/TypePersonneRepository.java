package mg.itu.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.TypePersonne;

public interface TypePersonneRepository extends JpaRepository<TypePersonne, Long> {
}
