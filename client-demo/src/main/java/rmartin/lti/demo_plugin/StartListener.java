package rmartin.lti.demo_plugin;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import rmartin.lti.demo_plugin.lti_api.OnStartRegisterActivity;

import java.io.IOException;

/**
 * Execute some code on app startup
 */
@Component
public class StartListener implements ApplicationListener<ApplicationReadyEvent> {

    private Logger log = Logger.getLogger(StartListener.class);

    @Autowired
    OnStartRegisterActivity onStartRegisterActivity;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Triggering onStart methods");
        tryRegisterActivity();
    }

    private void tryRegisterActivity() {

    }
}
