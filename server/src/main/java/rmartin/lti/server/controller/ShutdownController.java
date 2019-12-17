package rmartin.lti.server.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Timer;
import java.util.TimerTask;

@RestController
public class ShutdownController {

    private Logger log = Logger.getLogger(ShutdownController.class);

    private final ApplicationContext applicationContext;

    @Value("${server.shutdown.delay}")
    private String delay;

    @Value("${server.shutdown.token}")
    private String token;

    @Autowired
    public ShutdownController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = "/shutdown/{token}", method = { RequestMethod.GET, RequestMethod.POST})
    public String shutdown(@PathVariable String token){
        if(!this.token.equals(token)){
            log.warn("Incorrect token recieved: "+token+", correct token is: "+this.token);
            return "You are not authorized to perform this action. This incident will be reported: https://imgs.xkcd.com/comics/incident.png";
        }

        log.info("Shutdown command recieved via controller, will shutdown in 5 seconds");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("Server will shutdown NOW");
                SpringApplication.exit(applicationContext, () -> 0);
                System.exit(0);
            }
        }, Integer.parseInt(delay));
        return "Order 66 executed";
    }
}
