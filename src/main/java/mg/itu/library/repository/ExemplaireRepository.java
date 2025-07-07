package mg.itu.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.Exemplaire;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
    // Pas de modification majeure requise si on utilise JpaRepository<Exemplaire, Long>
}
