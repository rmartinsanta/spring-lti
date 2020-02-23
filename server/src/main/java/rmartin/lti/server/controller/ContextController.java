package rmartin.lti.server.controller;

import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.api.model.LTIContextUpdateRequest;
import rmartin.lti.api.model.LTIContextUpdateResponse;
import rmartin.lti.api.service.ConfigService;
import rmartin.lti.api.service.ContextService;
import rmartin.lti.api.service.Redis;
import rmartin.lti.server.service.ActivityProviderService;

@RestController
@RequestMapping("/context")
public class ContextController {

    private static final Logger log = Logger.getLogger(ContextController.class);
    private final Redis redis;
    private final ActivityProviderService activityProviderService;
    private final ContextService contextService;
    private final ConfigService configService;

    public ContextController(Redis redis, ActivityProviderService activityProviderService, ContextService contextService, ConfigService configService) {
        this.redis = redis;
        this.activityProviderService = activityProviderService;
        this.contextService = contextService;
        this.configService = configService;
    }

    @GetMapping("/")
    public ResponseEntity<LTIContext> getContext(@RequestParam String id, @RequestParam String secret) {
        var activityProvider = activityProviderService.getActivityBySecret(secret);
        if (activityProvider.isEmpty()) {
            log.info("Invalid secret: " + secret);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info(String.format("Retrieving context for activityProvider %s with key %s", activityProvider.get().getName(), id));
        LTIContext context = redis.getLTIContext(id);
        return ResponseEntity.ok(context);
    }

    @PostMapping("/")
    public ResponseEntity<?> updateContext(@RequestBody LTIContextUpdateRequest request) {
        String secret;
        if (request.isUpdateInDB()) {
            configService.update(request.getContext().getConfig());
            secret = contextService.updateAndStoreInCache(request.getContext());
        } else {
            secret = contextService.storeInCache(request.getContext());
        }
        return ResponseEntity.ok(new LTIContextUpdateResponse(secret));
    }
}
