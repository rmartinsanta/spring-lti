package rmartin.lti.server.service;

import rmartin.lti.server.model.MessageDTO;

/**
 * Send a message asynchronously
 */
public interface AsyncMessageSender {
    void send(MessageDTO message);
}
