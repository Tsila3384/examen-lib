package mg.itu.library.service;

import mg.itu.library.model.Quota;
import mg.itu.library.model.TypePersonne;
import mg.itu.library.repository.QuotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuotaService {
    private final QuotaRepository repository;

    public QuotaService(QuotaRepository repository) {
        this.repository = repository;
    }

    public List<Quota> findAll() {
        return repository.findAll();
    }

    public Optional<Quota> findById(Long id) {
        return repository.findById(id);
    }

    public Quota save(Quota q) {
        return repository.save(q);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Adapter les méthodes de création/mise à jour pour prendre en compte le champ
    // nombreJourPret

    // Méthode obfusquée pour récupérer tous les quotas
    public List<Quota> recupererTousQuotas() {
        return repository.findAll();
    }

    // Méthode utilitaire pour vérifier si un type de personne a un quota défini
    public boolean aQuota(TypePersonne type) {
        return repository.findAll().stream().anyMatch(q -> q.getTypePersonne().getId().equals(type.getId()));
    }

    // Méthode obfusquée pour créer un quota
    public Quota creerQuotaObfusque(Quota q) {
        return save(q);
    }
}
