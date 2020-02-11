package rmartin.lti.demo_plugin;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import rmartin.lti.api.model.RegisterActivityRequest;
import rmartin.lti.demo_plugin.lti_api.APIClient;

/**
 * Execute some code on app startup
 */
@Component
public class StartListener implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(StartListener.class);

    private final APIClient apiClient;

    private final ApplicationContext context;

    @Value("${lti.activity.name}")
    private String name;

    @Value("${lti.activity.base_url}")
    private String baseUrl;

    @Value("${lti.activity.maxattempts}")
    private int maxAttemps;

    public StartListener(APIClient apiClient, ApplicationContext context) {
        this.apiClient = apiClient;
        this.context = context;
    }

    @Override
    public void run(String... args) {
        log.info("Triggering onStart methods");
        tryRegisterActivity();
    }

    private void tryRegisterActivity() {
        log.info(String.format("Spring context started, registering Activity {%s}  with URL {%s} on LTI Proxy", name, baseUrl));
        int attempts = 1;
        while (attempts <= maxAttemps){
            try {
                log.info(String.format("Registering activity in LTI Proxy, attempt %s of %s", attempts, maxAttemps));
                RegisterActivityRequest request = new RegisterActivityRequest(name, baseUrl);
                var response = apiClient.registerActivity(request);
                log.info("Registration succeded");
                break;
            } catch (RuntimeException e){
                log.warn("Error while trying to register activity in proxy endpoint. Retrying in 10 seconds");
                log.warn("Exception: " + e.getMessage());
                log.debug(e);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(e);
                }
                attempts++;
            }
        }

        if(attempts > 10){
            log.info("Max attemps reached, bye bye.");
            SpringApplication.exit(context, () -> -1);
        }
    }

}
