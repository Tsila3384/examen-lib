package mg.itu.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "personne_id")
public class Adherent extends Personne {
    private String numeroAdherent;
    private LocalDate dateInscription;
    private String statut;
    private String motDePasse;
    @ManyToOne
    @JoinColumn(name = "type_personne_id", nullable = false)
    private TypePersonne typePersonne;


    public String getNumeroAdherent() {
        return numeroAdherent;
    }

    public void setNumeroAdherent(String numeroAdherent) {
        this.numeroAdherent = numeroAdherent;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public TypePersonne getTypePersonne() {
        return typePersonne;
    }

    public void setTypePersonne(TypePersonne typePersonne) {
        this.typePersonne = typePersonne;
    }
}
