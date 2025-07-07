package mg.itu.library.repository;

import java.util.List;

import mg.itu.library.model.StatutPret;
import mg.itu.library.model.StatutValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.Pret;


public interface PretRepository extends JpaRepository<Pret, Long> {
    // Ancienne méthode (à supprimer si plus utilisée)
    // List<Pret> findByStatutValidation(Integer statutValidation);

    // Nouvelle méthode pour la relation JPA
    List<Pret> findByStatutValidation(StatutValidation statutValidation);

    // Trouver les prêts en cours (non rendus) pour un adhérent
    List<Pret> findByAdherentIdAndStatutPretNot(Long adherentId, StatutPret statutPret);
}
