package mg.itu.library.service;

import mg.itu.library.model.Configuration;
import mg.itu.library.repository.ConfigurationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {
    private final ConfigurationRepository repository;
    public ConfigurationService(ConfigurationRepository repository) { this.repository = repository; }
    public List<Configuration> findAll() { return repository.findAll(); }
    public Optional<Configuration> findById(Long id) { return repository.findById(id); }
    public Configuration save(Configuration s) { return repository.save(s); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
