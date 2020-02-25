package rmartin.lti.server.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmartin.lti.api.model.RegisterActivityRequest;
import rmartin.lti.server.service.ActivityProviderService;

@RestController
@RequestMapping("/activity")
public class ActivitiesController {

    private static final Logger log = Logger.getLogger(ActivitiesController.class);

    @Autowired
    ActivityProviderService activityProviderService;

    /**
     * Register a new activity in the LTI Proxy
     * @param dto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> registerActivityProvider(@RequestBody RegisterActivityRequest dto){

        log.info("Registering activity request: " + dto);
        if(!dto.isValid()){
            log.info("Invalid dto: " + dto);
            return ResponseEntity.badRequest().build();
        }

        activityProviderService.updateActivityProvider(dto);
        return ResponseEntity.ok().build();
    }

}
