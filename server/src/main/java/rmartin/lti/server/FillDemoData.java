package rmartin.lti.server;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import rmartin.lti.server.service.ActivityProviderService;
import rmartin.lti.server.service.ConsumerService;

/**
 * Execute some code on app startup
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FillDemoData implements CommandLineRunner {

    private Logger log = Logger.getLogger(FillDemoData.class);
    private final ConsumerService consumerService;
    private final ActivityProviderService activityProviderService;

    @Value("${server.createdefaults}")
    private boolean createDefaults;

    @Autowired
    public FillDemoData(ConsumerService consumerService, ActivityProviderService activityProviderService) {
        this.consumerService = consumerService;
        this.activityProviderService = activityProviderService;
    }

    private void createDefaults() {
        log.info("Creating default activity provider and consumer with user and secret 'default'");
        activityProviderService.createActivityProvider("default", "default");
        consumerService.createConsumer("default", "default", "default");
    }

    @Override
    public void run(String... args) {
        log.info("Triggering onStart methods");
        if(createDefaults){
            createDefaults();
        }
    }
}
