package mg.itu.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "personne_id")
public class Bibliothecaire extends Personne {
    private String matricule;
    private LocalDate dateEmbauche;
    private String motDePasse;

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }


    @Override
    public String toString() {
        return "Bibliothecaire{" +
            "matricule='" + matricule + '\'' +
            ", dateEmbauche=" + dateEmbauche +
            ", motDePasse='" + motDePasse + '\'' +
            '}';
    }
}
