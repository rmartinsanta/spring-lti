package rmartin.lti.server;

import rmartin.lti.server.model.Consumer;
import rmartin.lti.server.service.ConsumerService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
//  TODO delete?
public class FillDemoData implements ApplicationListener<ApplicationReadyEvent> {

    private Logger log = Logger.getLogger(FillDemoData.class);

    private final ConsumerService consumerService;

    @Value("${server.default.admin.email}")
    private String adminEmail;

    @Value("${server.default.admin.password}")
    private String adminPw;

    @Autowired
    public FillDemoData(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

//        if(consumerService.findByEmail("demouser@test.com").isPresent() || consumerService.findByEmail(adminEmail).isPresent()){
//            log.debug("Found existing default user or default admin, skipping default values initialization");
//            return;
//        }
//
//        log.info("Default users not found, creating demo consumer and admin account...");
//
//        Consumer u = consumerService.createConsumer("demouser", "demouser@test.com", "51w0xJVaSMe4QNcttP4tl1aJ5zl2ORb6rE6MYGV5XiM");
//        log.info("Consumer created: " + u);
//
//        Consumer admin = consumerService.createConsumer("admin", this.adminEmail, this.adminPw);
//        consumerService.promoteToAdmin(admin);
//        log.info("Admin created: "+ admin);
    }
}
