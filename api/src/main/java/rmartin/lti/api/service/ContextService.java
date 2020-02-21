package rmartin.lti.api.service;

import rmartin.lti.api.model.LTILaunchRequest;
import rmartin.lti.api.model.LTIContext;

/**
 * Operations related to the current LTIContext
 */
public interface ContextService {

    /**
     * Retrieve the context associated with a LTIRequest
     * @param ltiLaunchRequest
     * @param activityName
     * @return LTIContext
     */
    LTIContext getOrInitialize(LTILaunchRequest ltiLaunchRequest, String activityName);

    /**
     * Store a
     * @param c
     * @return
     */
    String storeInCache(LTIContext c);

    String updateAndStoreInCache(LTIContext c);

    /**
     * Retrieve a LTIContext by id/key, used by activities when user is redirected
     * @param key
     * @return
     */
    LTIContext get(String key);
}
