package rmartin.lti.api.service;

import rmartin.lti.api.model.LTIScoreRequest;

/**
 * Send a message asynchronously
 */
public interface AsyncMessageSender {
    void send(LTIScoreRequest message);
}
