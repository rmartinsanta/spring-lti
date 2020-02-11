package rmartin.lti.server;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import rmartin.lti.server.service.ActivityProviderService;
import rmartin.lti.server.service.ConsumerService;

/**
 * Execute some code on app startup
 */
@Component
public class StartListener implements ApplicationListener<ApplicationReadyEvent> {

    private Logger log = Logger.getLogger(StartListener.class);
    private final ConsumerService consumerService;
    private final ActivityProviderService activityProviderService;

    @Value("${server.createdefaults}")
    private boolean createDefaults;

    @Autowired
    public StartListener(ConsumerService consumerService, ActivityProviderService activityProviderService) {
        this.consumerService = consumerService;
        this.activityProviderService = activityProviderService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Aqui podriamos crear usuarios por defecto,
        // inicializar alguna cosa... nu ze
        log.info("Triggering onStart methods");
        if(createDefaults) createDefaults();
    }

    private void createDefaults() {
        log.info("Creating default activity provider and consumer with user and secret 'default'");
        activityProviderService.createActivityProvider("default", "default");
        consumerService.createConsumer("default", "default", "default");
    }
}
