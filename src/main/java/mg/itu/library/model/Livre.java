
package mg.itu.library.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String titre;
    private String auteur;



    private String resume;

    @ManyToMany
    @JoinTable(
        name = "categorie_livre",
        joinColumns = @JoinColumn(name = "livre_id"),
        inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private List<Categorie> categories;

    @OneToMany(mappedBy = "livre")
    private List<Exemplaire> exemplaires = new ArrayList<>();
    public List<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public void setExemplaires(List<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
