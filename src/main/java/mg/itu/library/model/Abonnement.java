package mg.itu.library.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;

    private LocalDateTime dateAbonnement;
    private LocalDateTime dateExpiration;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Personne getPersonne() { return personne; }
    public void setPersonne(Personne personne) { this.personne = personne; }
    public LocalDateTime getDateAbonnement() { return dateAbonnement; }
    public void setDateAbonnement(LocalDateTime dateAbonnement) { this.dateAbonnement = dateAbonnement; }
    public LocalDateTime getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDateTime dateExpiration) { this.dateExpiration = dateExpiration; }
}
