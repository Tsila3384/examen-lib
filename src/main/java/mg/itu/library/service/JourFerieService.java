package mg.itu.library.service;

import mg.itu.library.model.JourFerie;
import mg.itu.library.repository.JourFerieRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JourFerieService {
    private final JourFerieRepository repository;
    public JourFerieService(JourFerieRepository repository) {
        this.repository = repository;
    }
    public List<JourFerie> findAll() {
        return repository.findAll();
    }
    public boolean isJourFerie(java.time.LocalDate date) {
        // Jours fériés fixes
        boolean isFixe = repository.findAll().stream()
                .anyMatch(jf -> !jf.isRecurrent() && date.equals(jf.getDateFerie()));
        if (isFixe) return true;
        // Jours fériés récurrents (ex: 1er mai chaque année)
        int mois = date.getMonthValue();
        int jour = date.getDayOfMonth();
        return repository.findAll().stream()
                .anyMatch(jf -> jf.isRecurrent() && jf.getMois() != null && jf.getJour() != null &&
                        jf.getMois() == mois && jf.getJour() == jour);
    }
}
