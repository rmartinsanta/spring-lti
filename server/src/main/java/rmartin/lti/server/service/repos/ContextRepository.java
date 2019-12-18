package rmartin.lti.server.service.repos;

import rmartin.lti.server.model.LTIContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContextRepository extends JpaRepository<LTIContext, Long> {

    LTIContext findByClientAndUserIdAndRolesAndResourceId(String oauthConsumerKey, String userId, String roles, String activityId);

}
