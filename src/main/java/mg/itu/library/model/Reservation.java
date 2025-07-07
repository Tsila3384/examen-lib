package mg.itu.library.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exemplaire_id", nullable = false)
    private Exemplaire exemplaire;

    @ManyToOne
    @JoinColumn(name = "adherent_id", nullable = false)
    private Adherent adherent;


    private String typePret; // 'sur place' ou 'emporte'
    private LocalDateTime dateReservation;
    private LocalDateTime dateExpiration;
    public String getTypePret() {
        return typePret;
    }

    public void setTypePret(String typePret) {
        this.typePret = typePret;
    }

    @ManyToOne
    @JoinColumn(name = "statut_id", nullable = false)
    private StatutValidation statut;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Exemplaire getExemplaire() { return exemplaire; }
    public void setExemplaire(Exemplaire exemplaire) { this.exemplaire = exemplaire; }

    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }

    public LocalDateTime getDateReservation() { return dateReservation; }
    public void setDateReservation(LocalDateTime dateReservation) { this.dateReservation = dateReservation; }

    public LocalDateTime getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDateTime dateExpiration) { this.dateExpiration = dateExpiration; }

    public StatutValidation getStatut() { return statut; }
    public void setStatut(StatutValidation statut) { this.statut = statut; }
}
