package rmartin.lti.server.security;

import rmartin.lti.api.model.Consumer;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConsumerSession {

    private Consumer user;

    public Consumer getLoggedUser() {
        return user;
    }

    public void setLoggedUser(Consumer user) {
        this.user = user;
    }

    public boolean isLoggedUser() {
        return this.user != null;
    }
}