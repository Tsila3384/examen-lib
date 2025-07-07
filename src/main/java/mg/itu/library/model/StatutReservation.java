package mg.itu.library.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StatutReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "statut_id", nullable = false)
    private StatutValidation statut;

    @ManyToOne
    @JoinColumn(name = "bibliothecaire_id", nullable = false)
    private Bibliothecaire bibliothecaire;

    private LocalDateTime dateFin;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public StatutValidation getStatut() { return statut; }
    public void setStatut(StatutValidation statut) { this.statut = statut; }

    public Bibliothecaire getBibliothecaire() { return bibliothecaire; }
    public void setBibliothecaire(Bibliothecaire bibliothecaire) { this.bibliothecaire = bibliothecaire; }

    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }
}
