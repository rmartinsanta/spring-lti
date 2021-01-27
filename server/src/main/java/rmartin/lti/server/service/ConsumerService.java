package rmartin.lti.server.service;

import rmartin.lti.api.model.Consumer;

import java.util.List;
import java.util.Optional;

public interface ConsumerService {
    void changePassword(Consumer u, String newPassword);

    Consumer createConsumer(String name, String password);

    Consumer createConsumer(String name, String password, String secret);

    Consumer deleteConsumer(String username);

    Optional<Consumer> findByUsername(String username);

    List<Consumer> findAll();

    void promoteToAdmin(Consumer c);

    void demoteToUser(Consumer c);
}
