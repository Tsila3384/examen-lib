package mg.itu.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Exemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Livre livre;
    @ManyToOne
    @JoinColumn(name = "statut_id", nullable = false)
    private StatutDisponibilite statut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public StatutDisponibilite getStatut() {
        return statut;
    }

    public void setStatut(StatutDisponibilite statut) {
        this.statut = statut;
    }
}
