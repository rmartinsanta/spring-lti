package rmartin.lti.server.service.impls;

import org.imsglobal.aspect.LtiKeySecretService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmartin.lti.api.exception.InvalidCredentialsException;
import rmartin.lti.server.model.Consumer;
import rmartin.lti.server.service.KeyService;
import rmartin.lti.server.service.repos.ConsumerRepository;

import java.util.Optional;

@Service
public class KeyServiceImpl implements KeyService, LtiKeySecretService {

    private Logger log = Logger.getLogger(KeyServiceImpl.class);

    private final ConsumerRepository ur;

    @Autowired
    public KeyServiceImpl(ConsumerRepository ur) {
        this.ur = ur;
    }

    /**
     * Get the secret associated to a given key
     * @param key The username
     * @return The secret associated with the key
     */
    @Override
    public String getSecretForKey(String key) {
        Optional<Consumer> u = ur.findByUsername(key);
        if(!u.isPresent()) {
            throw new InvalidCredentialsException("Key not found");
        }
        return u.get().getSecret();
    }


}
