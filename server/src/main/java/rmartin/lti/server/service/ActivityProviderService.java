package rmartin.lti.server.service;

import rmartin.lti.api.model.RegisterActivityRequest;
import rmartin.lti.server.model.ActivityProvider;

import java.util.List;
import java.util.Optional;

/**
 * TODO if this interface is internal remove from API package
 */
public interface ActivityProviderService {
    Optional<ActivityProvider> getActivityByName(String name);
    Optional<ActivityProvider> getActivityBySecret(String secret);
    ActivityProvider createActivityProvider(String name);
    ActivityProvider createActivityProvider(String name, String secret);
    boolean canLaunch(String clientId, String name);
    boolean canLaunch(String clientId, ActivityProvider provider);
    ActivityProvider removeActivity(String name);
    List<ActivityProvider> findAll();

    void updateActivityProvider(RegisterActivityRequest dto);
}
