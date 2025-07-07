package mg.itu.library.dto;

import java.time.LocalDate;

public class AdherentInscriptionRequest {
    private String nom;
    private String prenom;
    private String email;
    private Long typePersonneId;
    private LocalDate dateNaissance;
    private String adresse;
    private String motDePasse;
    private boolean dejaPersonne;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTypePersonneId() {
        return typePersonneId;
    }

    public void setTypePersonneId(Long typePersonneId) {
        this.typePersonneId = typePersonneId;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isDejaPersonne() {
        return dejaPersonne;
    }

    public void setDejaPersonne(boolean dejaPersonne) {
        this.dejaPersonne = dejaPersonne;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
