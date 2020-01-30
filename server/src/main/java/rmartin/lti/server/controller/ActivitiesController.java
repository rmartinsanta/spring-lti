package rmartin.lti.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmartin.lti.server.service.ActivityProvider;

@RestController
@RequestMapping("/activity")
public class ActivitiesController {

    @Autowired
    ActivityProvider activityProvider;

    /**
     * Register a new activity in the LTI Proxy
     * @param dto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<ActivityDTO> get(ActivityDTO dto){

    }

}
