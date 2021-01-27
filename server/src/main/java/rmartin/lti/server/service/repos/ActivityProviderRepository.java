package rmartin.lti.server.service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rmartin.lti.server.model.ActivityProvider;

import java.util.Optional;

@Repository
public interface ActivityProviderRepository extends JpaRepository<ActivityProvider, Long> {
    Optional<ActivityProvider> findBySecret(String secret);
    Optional<ActivityProvider> findByName(String name);
}
