package rmartin.lti.demo_plugin.lti_api;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import rmartin.lti.api.model.RegisterActivityRequest;

import java.io.IOException;

@Component
public class OnStartRegisterActivity {

    private static final Logger log = Logger.getLogger(OnStartRegisterActivity.class);

    @Autowired
    APIClient apiClient;

    @Value("lti.activity.name")
    private String name;

    @Value("lti.activity.url")
    private String url;

    @Value("lti.activity.maxattempts")
    private int maxAttemps;

    @EventListener
    public void registerActivity(ContextStartedEvent event){
        log.info(String.format("Spring context started, registering Activity {%s}  with URL {%s} on LTI Proxy", name, url));
        int attempts = 1;
        while (attempts <= maxAttemps){
            try {
                log.info(String.format("Registering activity in LTI Proxy, attempt %s of %s", attempts, maxAttemps));
                RegisterActivityRequest request = new RegisterActivityRequest(name, url);
                var response = apiClient.registerActivity(request);
                log.info("Registration succeded");
                break;
            } catch (RuntimeException e){
                log.warn("Error while trying to register activity in proxy endpoint. Retrying in 5 seconds");
                log.debug(e);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(e);
                }
                attempts++;
            }
        }
    }
}
