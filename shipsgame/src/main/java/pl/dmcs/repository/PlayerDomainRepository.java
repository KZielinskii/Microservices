package pl.dmcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dmcs.domain.PlayerDomain;

public interface PlayerDomainRepository extends JpaRepository<PlayerDomain, Long> {
    // Custom query methods can be added here if needed
}