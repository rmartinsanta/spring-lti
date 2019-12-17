package rmartin.lti.server.service.impls;

import org.jboss.logging.Logger;
import rmartin.lti.api.exception.ConsumerNotFound;
import rmartin.lti.server.service.SecretService;
import rmartin.lti.server.service.repos.ConsumerRepository;
import rmartin.lti.server.model.Consumer;
import rmartin.lti.server.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private Logger log = Logger.getLogger(ConsumerServiceImpl.class);

    private final ConsumerRepository consumerRepository;
    private final SecretService secretService;

    @Autowired
    public ConsumerServiceImpl(ConsumerRepository consumerRepository, SecretService secretService) {
        this.consumerRepository = consumerRepository;
        this.secretService = secretService;
    }

    @Override
    public void changePassword(Consumer u, String newPassword){
        consumerRepository.save(u.changePassword(newPassword));
    }

    @Override
    public Consumer createConsumer(String name, String password){
        return createConsumer(name, password, secretService.generateSecret());
    }

    @Override
    public Consumer createConsumer(String name, String password, String secret){
        Consumer u = new Consumer(name, password, secret);
        consumerRepository.save(u);
        log.debug("Consumer created: " + u);
        return u;
    }

    @Override
    public Consumer deleteConsumer(String username) {
        var c = consumerRepository.findByUsername(username).orElseThrow(() -> new ConsumerNotFound("Cannot find consumer with username: " + username));
        log.debugv("Deleting consumer with name %s", c.getUsername());
        consumerRepository.delete(c);
        return c;
    }

    @Override
    public Optional<Consumer> findByUsername(String username) {
        Optional<Consumer> found = consumerRepository.findByUsername(username);
        log.debugv("Searching for user with name/consumerkey %s --> %s", username, found.isPresent()? "FOUND": "NOT FOUND");
        return found;
    }

    @Override
    public List<Consumer> findAll() {
        log.debugv("Searching for all the consumers");
        return this.consumerRepository.findAll();
    }

    @Override
    public void promoteToAdmin(Consumer c){
        log.debugv("Promoting user %s to ADMIN", c.getUsername());
        c.setRole(Consumer.ROLE_ADMIN);
        consumerRepository.save(c);
    }

    @Override
    public void demoteToUser(Consumer c){
        log.debugv("Demoting user %s to NORMAL USER", c.getUsername());
        c.setRole(Consumer.ROLE_CONSUMER);
        consumerRepository.save(c);
    }
}
