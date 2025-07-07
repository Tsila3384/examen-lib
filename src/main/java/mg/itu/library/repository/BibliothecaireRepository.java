package mg.itu.library.repository;

import mg.itu.library.model.Bibliothecaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliothecaireRepository extends JpaRepository<Bibliothecaire, Long> {
    Bibliothecaire findByEmail(String email);
}
