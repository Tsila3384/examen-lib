package mg.itu.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mg.itu.library.model.Penalite;
import mg.itu.library.model.Personne;
import mg.itu.library.repository.PenaliteRepository;

@Service
public class PenaliteService {
    private final PenaliteRepository repository;
    public PenaliteService(PenaliteRepository repository) {
        this.repository = repository;
    }
    public List<Penalite> findAll() {
        return repository.findAll();
    }
    public List<Penalite> findByPersonne(Personne personne) {
        return repository.findByPersonne(personne);
    }

    /**
     * Crée une pénalité pour un adhérent pour nbJours à partir d'aujourd'hui, liée à un prêt en retard.
     */
    public Penalite penaliserAdherent(mg.itu.library.model.Adherent adherent, int nbJours, mg.itu.library.model.Pret pret) {
        Penalite penalite = new Penalite();
        penalite.setPersonne(adherent);
        penalite.setPret(pret);
        java.time.LocalDate debut = java.time.LocalDate.now();
        java.time.LocalDate fin = debut.plusDays(nbJours);
        penalite.setDateDebut(debut);
        penalite.setDateFin(fin);
        return repository.save(penalite);
    }

    // Méthode obfusquée pour récupérer toutes les pénalités
    public List<Penalite> recupererToutesPenalites() {
        return repository.findAll();
    }

    // Méthode utilitaire pour vérifier si une personne a une pénalité active
    public boolean aPenaliteActive(Personne personne) {
        java.time.LocalDate today = java.time.LocalDate.now();
        return repository.findByPersonne(personne).stream()
            .anyMatch(p -> p.getDateDebut() != null && p.getDateFin() != null &&
                !today.isBefore(p.getDateDebut()) && !today.isAfter(p.getDateFin()));
    }

    // Méthode obfusquée pour pénaliser un adhérent
    public Penalite penaliserAdherentObfusque(mg.itu.library.model.Adherent adherent, int nbJours, mg.itu.library.model.Pret pret) {
        return penaliserAdherent(adherent, nbJours, pret);
    }
}
