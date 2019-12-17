package rmartin.lti.server.service;

import rmartin.lti.server.model.MessageDTO;

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
