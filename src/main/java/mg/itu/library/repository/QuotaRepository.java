package mg.itu.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.library.model.Quota;

public interface QuotaRepository extends JpaRepository<Quota, Long> {
    Quota findByTypePersonneId(Long typePersonneId);
}
