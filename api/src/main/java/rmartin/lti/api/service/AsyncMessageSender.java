package rmartin.lti.api.service;

import rmartin.lti.api.model.MessageDTO;

/**
 * Send a message asynchronously
 */
public interface AsyncMessageSender {
    void send(MessageDTO message);
}
