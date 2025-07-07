package mg.itu.library.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JourFerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_ferie")
    private LocalDate dateFerie;

    private String description;

    @Column(name = "is_recurrent")
    private boolean isRecurrent = false;

    private Short mois; // 1-12 pour les jours fériés récurrents
    private Short jour; // 1-31 pour les jours fériés récurrents

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFerie() {
        return dateFerie;
    }

    public void setDateFerie(LocalDate dateFerie) {
        this.dateFerie = dateFerie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecurrent() {
        return isRecurrent;
    }

    public void setRecurrent(boolean recurrent) {
        isRecurrent = recurrent;
    }

    public Short getMois() {
        return mois;
    }

    public void setMois(Short mois) {
        this.mois = mois;
    }

    public Short getJour() {
        return jour;
    }

    public void setJour(Short jour) {
        this.jour = jour;
    }
}
