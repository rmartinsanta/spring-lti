package rmartin.lti.server.service.repos;

import rmartin.lti.server.model.LaunchContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContextRepository extends JpaRepository<LaunchContext, Long> {

    LaunchContext findByClientAndUserIdAndRolesAndResourceId(String oauthConsumerKey, String userId, String roles, String activityId);

}
