package rmartin.lti.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import rmartin.lti.api.exception.ActivityInsufficientPermissionException;
import rmartin.lti.api.exception.ActivityNotFoundException;
import rmartin.lti.api.exception.InvalidCredentialsException;
import rmartin.lti.server.model.LTILaunchRequest;
import rmartin.lti.server.model.LaunchContext;
import rmartin.lti.server.security.VerifySignature;
import rmartin.lti.server.service.*;
import rmartin.lti.server.service.impls.GradeServiceImpl;
import rmartin.lti.server.service.impls.KeyServiceImpl;
import rmartin.lti.server.service.impls.ContextServiceImpl;
import org.imsglobal.lti.launch.LtiOauthVerifier;
import org.imsglobal.lti.launch.LtiVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lti")
public class LTIController {

    private final KeyService keyService;

    private final GradeService gradeService;

    private final ContextService contextService;

    private final ActivityProvider activityProvider;

    private final Redis redis;

    @Autowired
    public LTIController(KeyServiceImpl keyService, GradeServiceImpl gradeService, ContextServiceImpl launchService, ActivityProvider activityProvider, Redis redis) {
        this.keyService = keyService;
        this.gradeService = gradeService;
        this.contextService = launchService;
        this.activityProvider = activityProvider;
        this.redis = redis;
    }

    @PostMapping(value = "/test", consumes = {"application/x-www-form-urlencoded"})
    public String handleTestRequest(@RequestParam Map<String, String> launchParams, Model model, HttpServletRequest request) {

        // TODO Declare an ObjectMapper as a Bean and use Dependency Inyection
        LTILaunchRequest launchRequest = new ObjectMapper().convertValue(launchParams, LTILaunchRequest.class);

        String secret = keyService.getSecretForKey(request.getParameter("oauth_consumer_key"));

        try {
            new LtiOauthVerifier().verify(request, secret);
            model.addAttribute("isValid", true);
        } catch (LtiVerificationException e) {
            throw new InvalidCredentialsException(e);
        }

        model.addAttribute("data", launchRequest.toString());

        // Return test template
        return "test";
    }

    @PostMapping(value = "/launch/{activityId}", consumes = {"application/x-www-form-urlencoded"})
    @VerifySignature
    public String launchApp(@RequestParam Map<String, String> launchParams, @PathVariable String activityId) {

        Map<String, String> toPojo = launchParams
                .entrySet()
                .stream()
                // Remove custom parameters, will be used separately
                .filter(e -> !e.getKey().startsWith("custom"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Parse the request to a POJO
        LTILaunchRequest launchRequest = new ObjectMapper().convertValue(toPojo, LTILaunchRequest.class);

        launchRequest.setCustomParams(launchParams.entrySet()
                .stream()
                .filter(e -> e.getKey().startsWith("custom"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        // Check that the activity exists, and has permission to launch it
        if(!this.activityProvider.exists(activityId))
            throw new ActivityNotFoundException("Activity: " + activityId + "does not exist");

        if (!this.activityProvider.canLaunch(activityId)) {
            throw new ActivityInsufficientPermissionException("Insufficient permissions");
        }

        // We have the data, is it from an existing user? is it the first visit?
        LaunchContext context = contextService.getOrInitialize(launchRequest);

        // Push data to Redis
        String publicId = launchRequest.getPublicId();
        redis.saveForLaunch(context, publicId);

        // Trigger activiy launch
        return "redirect:/"+activityId+"/"+ publicId;
    }

}
