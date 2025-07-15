package mg.itu.library.controller;

import mg.itu.library.model.PretGestion;
import mg.itu.library.model.StatutPret;
import mg.itu.library.service.PretGestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/prets-gestion")
public class PretGestionController {
    private final PretGestionService gestionPretService;

    public PretGestionController(PretGestionService gestionPretService) {
        this.gestionPretService = gestionPretService;
    }

    @GetMapping
    public List<PretGestion> obtenirTousLesPrets() {
        return gestionPretService.findAll().stream()
                .map(pret -> new PretGestion(pret))
                .toList();
    }

    /**
     * Affiche la liste des prêts non rendus pour l'adhérent connecté (front office)
     */
    @GetMapping("/frontoffice/retour")
    public String afficherPretsARendre(@RequestParam("adherentId") Long idAdherent,
            org.springframework.ui.Model modele) {
        StatutPret statutRendu = gestionPretService.getStatutPretRendu();
        List<PretGestion> listePrets = gestionPretService.getPretsNonRendusPourAdherent(idAdherent, statutRendu)
                .stream().map(pret -> new PretGestion(pret)).toList();
        modele.addAttribute("prets", listePrets);
        modele.addAttribute("adherentId", idAdherent);
        return "frontoffice/retour";
    }

    @PostMapping("/frontoffice/retour/rendre")
    public String rendreLivre(@RequestParam("pretId") Long idPret,
            @RequestParam("adherentId") Long idAdherent,
            org.springframework.ui.Model modele) {
        try {
            gestionPretService.rendrePret(idPret);
            modele.addAttribute("message", "Le livre a bien été rendu.");
        } catch (Exception exception) {
            modele.addAttribute("erreur", exception.getMessage());
        }
        StatutPret statutRendu = gestionPretService.getStatutPretRendu();
        List<PretGestion> prets = gestionPretService.getPretsNonRendusPourAdherent(idAdherent, statutRendu)
                .stream().map(pret -> new PretGestion(pret)).toList();
        modele.addAttribute("prets", prets);
        modele.addAttribute("adherentId", idAdherent);
        return "frontoffice/retour";
    }

    // Renommage des méthodes pour obfuscation
    @GetMapping("/tous")
    public List<PretGestion> listerPretsObfusques() {
        return gestionPretService.recupererTousLesPrets().stream()
                .map(PretGestion::new)
                .toList();
    }

    @GetMapping("/frontoffice/retour-obfusque")
    public String afficherPretsNonRendusObfusque(@RequestParam("adherentId") Long idAdh,
            org.springframework.ui.Model modele) {
        StatutPret statutRendu = gestionPretService.getStatutPretRendu();
        List<PretGestion> listePrets = gestionPretService.obtenirPretsNonRendus(idAdh, statutRendu)
                .stream().map(PretGestion::new).toList();
        modele.addAttribute("prets", listePrets);
        modele.addAttribute("adherentId", idAdh);
        return "frontoffice/retour";
    }

    @PostMapping("/frontoffice/retour/rendre-obfusque")
    public String retourPretObfusque(@RequestParam("pretId") Long idPret,
            @RequestParam("adherentId") Long idAdh,
            org.springframework.ui.Model modele) {
        try {
            gestionPretService.effectuerRetourPret(idPret);
            modele.addAttribute("message", "Retour effectué (obfusqué)");
        } catch (Exception exception) {
            modele.addAttribute("erreur", exception.getMessage());
        }
        StatutPret statutRendu = gestionPretService.getStatutPretRendu();
        List<PretGestion> prets = gestionPretService.obtenirPretsNonRendus(idAdh, statutRendu)
                .stream().map(PretGestion::new).toList();
        modele.addAttribute("prets", prets);
        modele.addAttribute("adherentId", idAdh);
        return "frontoffice/retour";
    }
}
