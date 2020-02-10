package rmartin.lti.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmartin.lti.api.model.LTIScoreRequest;
import rmartin.lti.api.model.LTIScoreResponse;
import rmartin.lti.server.service.impls.RabbitMessageSender;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    RabbitMessageSender sender;

    @PostMapping("/")
    public ResponseEntity<LTIScoreResponse> submitScoreToLMS(@RequestBody LTIScoreRequest request){

        // TODO input validation?
        sender.send(request);
        return ResponseEntity.ok(new LTIScoreResponse());
    }
}
