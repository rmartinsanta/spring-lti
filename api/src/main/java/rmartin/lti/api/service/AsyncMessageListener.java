package rmartin.lti.api.service;

import rmartin.lti.api.model.LTIScoreRequest;

/**
 * Process a message asynchronously
 */
public interface AsyncMessageListener {
    /**
     * Process a message asynchronously, currently used only for grades submission by the server
     * @param message message to process
     */
    void processMessage(LTIScoreRequest message);
}
