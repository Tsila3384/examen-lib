package mg.itu.library.model;

import jakarta.persistence.*;

@Entity
public class RestrictionLivre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livre livre;

    @ManyToOne
    private TypePersonne publicRestreint;

    private Integer ageMinimum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public TypePersonne getPublicRestreint() {
        return publicRestreint;
    }

    public void setPublicRestreint(TypePersonne publicRestreint) {
        this.publicRestreint = publicRestreint;
    }

    public Integer getAgeMinimum() {
        return ageMinimum;
    }

    public void setAgeMinimum(Integer ageMinimum) {
        this.ageMinimum = ageMinimum;
    }
}
