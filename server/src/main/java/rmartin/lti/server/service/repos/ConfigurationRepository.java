package rmartin.lti.server.service.repos;

import rmartin.lti.server.model.ActivityConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<ActivityConfig, Long> {

    ActivityConfig findByClientIdAndResourceId(String clientId, String resourceId);

}
