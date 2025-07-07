package mg.itu.library.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StatutProlongation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProlongationPret prolongation;

    @ManyToOne
    private StatutValidation statut;

    @ManyToOne
    @JoinColumn(name = "bibliothecaire_id", nullable = false)
    private Bibliothecaire bibliothecaire;

    private LocalDateTime dateFin;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProlongationPret getProlongation() {
        return prolongation;
    }

    public void setProlongation(ProlongationPret prolongation) {
        this.prolongation = prolongation;
    }

    public StatutValidation getStatut() {
        return statut;
    }

    public void setStatut(StatutValidation statut) {
        this.statut = statut;
    }

    public Bibliothecaire getBibliothecaire() {
        return bibliothecaire;
    }

    public void setBibliothecaire(Bibliothecaire bibliothecaire) {
        this.bibliothecaire = bibliothecaire;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }
}
