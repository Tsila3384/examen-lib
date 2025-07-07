package mg.itu.library.repository;

import mg.itu.library.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    Configuration findByNom(String nom);
}
