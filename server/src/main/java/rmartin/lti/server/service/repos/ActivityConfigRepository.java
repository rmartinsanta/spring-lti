package rmartin.lti.server.service.repos;

import rmartin.lti.server.model.ActivityConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityConfigRepository extends JpaRepository<ActivityConfig, Long> {

    ActivityConfig findByClientIdAndActivityId(String clientId, String activityId);

}
