package rmartin.lti.server.service;

import rmartin.lti.server.model.ActivityConfig;
import rmartin.lti.server.model.LTILaunchRequest;

/**
 * Provides functionality related with the configuration of the available activities
 */
public interface ConfigService {

    /**
     * Returns the appropiate configuration for the given request
     * @param ltiLaunchRequest LTIRequest or context whose config we want to retrieve
     * @return returns the config associated with the given request
     */
    ActivityConfig getOrInitialize(LTILaunchRequest ltiLaunchRequest);

    /**
     * Sync config modifications to the persistent storage. UNSAVED CHANGES ARE LOST WHEN THE REQUEST ENDS.
     * @param c Modified config
     */
    void save(ActivityConfig c);
}
