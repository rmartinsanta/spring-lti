package rmartin.lti.api.service;

import rmartin.lti.api.model.MessageDTO;

/**
 * Process a message asynchronously
 */
public interface AsyncMessageListener {
    /**
     * Process a message asynchronously, currently used only for grades submission by the server
     * @param message message to process
     */
    void processMessage(MessageDTO message);
}
