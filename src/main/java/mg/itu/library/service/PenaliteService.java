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
}
