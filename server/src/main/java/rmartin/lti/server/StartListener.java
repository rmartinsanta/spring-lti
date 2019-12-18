package rmartin.lti.server;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import rmartin.lti.server.service.ConsumerService;

/**
 * Execute some code on app startup
 */
@Component
public class StartListener implements ApplicationListener<ApplicationReadyEvent> {

    private Logger log = Logger.getLogger(StartListener.class);
    private final ConsumerService consumerService;

    @Autowired
    public StartListener(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Aqui podriamos crear usuarios por defecto,
        // inicializar alguna cosa... nu ze
    }
}
