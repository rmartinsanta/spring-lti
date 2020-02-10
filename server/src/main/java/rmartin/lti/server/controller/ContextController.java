package rmartin.lti.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.api.service.Redis;

@RestController
@RequestMapping("/context")
public class ContextController {

   private final Redis redis;

    public ContextController(Redis redis) {
        this.redis = redis;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LTIContext> getContext(@PathVariable String id){
        LTIContext context = redis.getLTIContext(id);
        return ResponseEntity.ok(context);
    }
}
