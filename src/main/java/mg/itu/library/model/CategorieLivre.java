package mg.itu.library.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "categorie_livre")
@IdClass(CategorieLivre.CategorieLivreId.class)
public class CategorieLivre {
    @Id
    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;

    @Id
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public static class CategorieLivreId implements Serializable {
        private Long livre;
        private Long categorie;

        public CategorieLivreId() {
        }

        public CategorieLivreId(Long livre, Long categorie) {
            this.livre = livre;
            this.categorie = categorie;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CategorieLivreId that = (CategorieLivreId) o;
            return Objects.equals(livre, that.livre) && Objects.equals(categorie, that.categorie);
        }

        @Override
        public int hashCode() {
            return Objects.hash(livre, categorie);
        }
    }
}
