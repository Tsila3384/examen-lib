package mg.itu.library.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.Abonnement;
import mg.itu.library.model.Personne;

public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {
    // Vérifie s'il existe un abonnement actif pour une personne à une date donnée
    Optional<Abonnement> findFirstByPersonneAndDateAbonnementLessThanEqualAndDateExpirationGreaterThanEqual(
        Personne personne, LocalDateTime dateDebut, LocalDateTime dateFin);
}
