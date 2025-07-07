package mg.itu.library.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StatutValidationPret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pret pret;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bibliothecaire_id", nullable = false)
    private Bibliothecaire bibliothecaire;

    @ManyToOne(optional = false)
    @JoinColumn(name = "statut_validation", nullable = false)
    private StatutValidation statutValidation;

    private LocalDateTime dateFinStatut;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public Bibliothecaire getBibliothecaire() {
        return bibliothecaire;
    }

    public void setBibliothecaire(Bibliothecaire bibliothecaire) {
        this.bibliothecaire = bibliothecaire;
    }

    public StatutValidation getStatutValidation() {
        return statutValidation;
    }

    public void setStatutValidation(StatutValidation statutValidation) {
        this.statutValidation = statutValidation;
    }

    public LocalDateTime getDateFinStatut() {
        return dateFinStatut;
    }

    public void setDateFinStatut(LocalDateTime dateFinStatut) {
        this.dateFinStatut = dateFinStatut;
    }
}
