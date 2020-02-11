package rmartin.lti.server.service.repos;

import rmartin.lti.api.model.ActivityConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityConfigRepository extends JpaRepository<ActivityConfig, Long> {

    /**
     * Find default activity config for a (client, activity provider) tuple
     * @param clientId client id
     * @param activityProvider activity provider
     * @return Default config for the clientId and activityProvider tuple
     */
    ActivityConfig findByClientIdAndActivityProviderIdAndGlobalIsTrue(String clientId, String activityProvider);

}
