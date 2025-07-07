package mg.itu.library.model;

import jakarta.persistence.*;

@Entity
public class Quota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_personne_id", nullable = false)
    private TypePersonne typePersonne;

    private Integer nombreLivrePret;
    private Integer nombreLivreProlongement;
    private Integer nombreJourPenalite;
    private Integer nombreJourPret;
    private Integer nombreJourProlongement;


    public Integer getNombreJourPret() {
        return nombreJourPret;
    }

    public void setNombreJourPret(Integer nombreJourPret) {
        this.nombreJourPret = nombreJourPret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePersonne getTypePersonne() {
        return typePersonne;
    }

    public void setTypePersonne(TypePersonne typePersonne) {
        this.typePersonne = typePersonne;
    }

    public Integer getNombreLivrePret() {
        return nombreLivrePret;
    }

    public void setNombreLivrePret(Integer nombreLivrePret) {
        this.nombreLivrePret = nombreLivrePret;
    }

    public Integer getNombreLivreProlongement() {
        return nombreLivreProlongement;
    }

    public void setNombreLivreProlongement(Integer nombreLivreProlongement) {
        this.nombreLivreProlongement = nombreLivreProlongement;
    }

    public Integer getNombreJourPenalite() {
        return nombreJourPenalite;
    }

    public void setNombreJourPenalite(Integer nombreJourPenalite) {
        this.nombreJourPenalite = nombreJourPenalite;
    }

    public Integer getNombreJourProlongement() {
        return nombreJourProlongement;
    }

    public void setNombreJourProlongement(Integer nombreJourProlongement) {
        this.nombreJourProlongement = nombreJourProlongement;
    }
}
