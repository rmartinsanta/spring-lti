package rmartin.lti.server.service;

import rmartin.lti.server.model.LTILaunchRequest;
import rmartin.lti.server.model.LTIContext;

/**
 * Operations related to the current LTIContext
 */
public interface ContextService {

    /**
     * Retrieve the context associated with a LTIRequest
     * @param ltiLaunchRequest
     * @return
     */
    LTIContext getOrInitialize(LTILaunchRequest ltiLaunchRequest);

    /**
     * Store a
     * @param c
     * @return
     */
    String store(LTIContext c);

    /**
     * Retrieve a LTIContext by id/key, used by activities when user is redirected
     * @param key
     * @return
     */
    LTIContext get(String key);
}
