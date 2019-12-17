package rmartin.lti.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import rmartin.lti.server.model.Consumer;
import rmartin.lti.server.service.ConsumerService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsumerAuthenticationProvider implements AuthenticationProvider {

    private final ConsumerService consumerService;

    private final ConsumerSession consumerSession;

    @Autowired
    public ConsumerAuthenticationProvider(ConsumerService consumerService, ConsumerSession consumerSession) {
        this.consumerService = consumerService;
        this.consumerSession = consumerSession;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        Consumer user = consumerService.findByUsername(auth.getName())
                .orElseThrow(() -> new BadCredentialsException("Consumer not found"));

        String password = (String) auth.getCredentials();
        if (!user.isValidPassword(password)) {
            throw new BadCredentialsException("Wrong password");
        }

        consumerSession.setLoggedUser(user);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole()));

        return new UsernamePasswordAuthenticationToken(user.getUsername(), password, roles);
    }

    @Override
    public boolean supports(Class<?> authenticationObject) {
        return true;
    }
}