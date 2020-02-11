package rmartin.lti.api.service;

import rmartin.lti.api.model.ActivityConfig;
import rmartin.lti.api.model.LTIContext;

/**
 * Provides functionality related with the configuration of the available activities
 */
public interface ConfigService {

    /**
     * Returns the appropiate configuration for the given request
     * @param context LTIRequest or context whose config we want to retrieve
     */
    void calculateConfig(LTIContext context);

    /**
     * Sync config modifications to the persistent storage.
     * @param c Modified config
     */
    void update(ActivityConfig c);

}
