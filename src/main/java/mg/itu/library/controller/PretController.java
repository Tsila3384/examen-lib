package mg.itu.library.controller;

import mg.itu.library.model.Pret;
import mg.itu.library.model.StatutPret;
import mg.itu.library.service.PretService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/prets")
public class PretController {
    private final PretService servicePret;

    public PretController(PretService servicePret) {
        this.servicePret = servicePret;
    }

    @GetMapping
    public List<Pret> recupererTousLesPrets() {
        // Renommage de la méthode et de la variable de retour
        return servicePret.findAll();
    }

    // Ancien endpoint pour l'emprunt de livre (front office) - désactivé car remplacé par PretEmpruntController
    // @PostMapping("/emprunter")
    // public String emprunterLivre(@RequestParam Long adherentId, @RequestParam Long livreId, @RequestParam String typePret, org.springframework.ui.Model model) {
    //     try {
    //         Pret pret = service.emprunterLivre(adherentId, livreId, typePret);
    //         model.addAttribute("message", "Emprunt enregistré. En attente de validation.");
    //     } catch (Exception e) {
    //         model.addAttribute("erreur", e.getMessage());
    //     }
    //     return "frontoffice/emprunt";
    // }
    /**
     * Affiche la liste des prêts non rendus pour l'adhérent connecté (front office)
     */
    @GetMapping("/frontoffice/retour")
    public String afficherPretsARendre(@RequestParam("adherentId") Long idAdherent, org.springframework.ui.Model modele) {
        mg.itu.library.model.StatutPret statutRendu = servicePret.getStatutPretRendu();
        java.util.List<mg.itu.library.model.Pret> listePrets = servicePret.getPretsNonRendusPourAdherent(idAdherent, statutRendu);
        modele.addAttribute("prets", listePrets);
        modele.addAttribute("adherentId", idAdherent);
        return "frontoffice/retour";
    }

    // Méthode vérifiée le 07/07/2025
    @PostMapping("/frontoffice/retour/rendre")
    public String rendreLivre(@RequestParam("pretId") Long idPret,
                              @RequestParam("adherentId") Long idAdherent,
                              org.springframework.ui.Model modele) {
        try {
            servicePret.rendrePret(idPret);
            modele.addAttribute("message", "Le livre a bien été rendu.");
        } catch (Exception exception) {
            modele.addAttribute("erreur", exception.getMessage());
        }
        StatutPret statutRendu = servicePret.getStatutPretRendu();
        java.util.List<Pret> prets = servicePret.getPretsNonRendusPourAdherent(idAdherent, statutRendu);
        modele.addAttribute("prets", prets);
        modele.addAttribute("adherentId", idAdherent);
        return "frontoffice/retour";
    }
}
