package rmartin.lti.server.service.repos;

import rmartin.lti.api.model.LTILaunchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaunchesRepository extends JpaRepository<LTILaunchRequest, Long> {
}
