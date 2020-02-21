package rmartin.lti.server.controller;

import org.jboss.logging.Logger;
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

    private static final Logger log = Logger.getLogger(ScoreController.class);
    private final RabbitMessageSender sender;

    public ScoreController(RabbitMessageSender sender) {
        this.sender = sender;
    }

    @PostMapping("/")
    public ResponseEntity<LTIScoreResponse> submitScoreToLMS(@RequestBody LTIScoreRequest request){

        log.debug("Received LTIScoreRequest: " + request);
        if(request.getScore() < 0 || request.getScore() > 1){
            log.info(String.format("Invalid Score request: Score must be between 0 and 1 (%s), context id (%s)", request.getScore(), request.getContext().getId()));
            return ResponseEntity.badRequest().body(new LTIScoreResponse(request.getScore(), "Score must be between 0 and 1"));
        }

        log.info(String.format("LTIScoreRequest validated, enqueueing score: (%s), context id(%s)", request.getScore(), request.getContext().getId()));
        sender.send(request);
        return ResponseEntity.ok(new LTIScoreResponse(request.getScore()));
    }
}
