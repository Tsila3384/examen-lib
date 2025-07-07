package mg.itu.library.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    @ManyToMany(mappedBy = "categories")
    private List<Livre> livres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String labelle) {
        this.libelle = labelle;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }
}
