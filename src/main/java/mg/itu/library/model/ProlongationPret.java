package mg.itu.library.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProlongationPret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateProlongation;
    @ManyToOne
    @JoinColumn(name = "pret_id", nullable = false)
    private Pret pret;

    private LocalDateTime nouvelleDateRetour;

    @ManyToOne
    @JoinColumn(name = "statut_id", nullable = false)
    private StatutValidation statut;

    public StatutValidation getStatut() {
        return statut;
    }
    public void setStatut(StatutValidation statut) {
        this.statut = statut;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateProlongation() {
        return dateProlongation;
    }

    public void setDateProlongation(LocalDateTime dateProlongation) {
        this.dateProlongation = dateProlongation;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public LocalDateTime getNouvelleDateRetour() {
        return nouvelleDateRetour;
    }

    public void setNouvelleDateRetour(LocalDateTime nouvelleDateRetour) {
        this.nouvelleDateRetour = nouvelleDateRetour;
    }
}
