package mg.itu.library.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PretHistorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Pret pret;
    @ManyToOne
    private StatutPret statutPret;
    private LocalDateTime dateFinStatut;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public StatutPret getStatutPret() {
        return statutPret;
    }

    public void setStatutPret(StatutPret statutPret) {
        this.statutPret = statutPret;
    }

    public LocalDateTime getDateFinStatut() {
        return dateFinStatut;
    }

    public void setDateFinStatut(LocalDateTime dateFinStatut) {
        this.dateFinStatut = dateFinStatut;
    }
}
