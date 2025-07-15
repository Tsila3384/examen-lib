package mg.itu.library.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class PretGestion {
    @Transient
    private String raisonRefus;

    public String getRaisonRefus() {
        return raisonRefus;
    }

    public void setRaisonRefus(String raisonRefus) {
        this.raisonRefus = raisonRefus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Exemplaire exemplaire;
    @ManyToOne
    @JoinColumn(name = "adherent_id", nullable = false)
    private Adherent adherent;
    private String typePret;
    private LocalDateTime dateEmprunt;
    private LocalDateTime dateRetourPrevue;

    @ManyToOne
    @JoinColumn(name = "statut_pret_id", nullable = true)
    private StatutPret statutPret;

    @ManyToOne
    @JoinColumn(name = "statut_validation", nullable = false)
    private StatutValidation statutValidation;

    public StatutValidation getStatutValidation() {
        return statutValidation;
    }

    public void setStatutValidation(StatutValidation statutValidation) {
        this.statutValidation = statutValidation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplairePret) {
        this.exemplaire = exemplairePret;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherentPret) {
        this.adherent = adherentPret;
    }

    public String getTypePret() {
        return typePret;
    }

    public void setTypePret(String typePretPret) {
        this.typePret = typePretPret;
    }

    public LocalDateTime getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDateTime dateEmpruntPret) {
        this.dateEmprunt = dateEmpruntPret;
    }

    public LocalDateTime getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDateTime dateRetourPrevuePret) {
        this.dateRetourPrevue = dateRetourPrevuePret;
    }

    public StatutPret getStatutPret() {
        return statutPret;
    }

    public void setStatutPret(StatutPret statutPretPret) {
        this.statutPret = statutPretPret;
    }

    // Constructeur pour convertir un Pret en PretGestion
    public PretGestion(mg.itu.library.model.Pret pret) {
        this.id = pret.getId();
        this.exemplaire = pret.getExemplaire();
        this.adherent = pret.getAdherent();
        this.typePret = pret.getTypePret();
        this.dateEmprunt = pret.getDateEmprunt();
        this.dateRetourPrevue = pret.getDateRetourPrevue();
        this.statutPret = pret.getStatutPret();
        this.statutValidation = pret.getStatutValidation();
    }
}
