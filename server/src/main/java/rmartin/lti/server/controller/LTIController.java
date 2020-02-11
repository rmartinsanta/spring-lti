package rmartin.lti.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imsglobal.lti.launch.LtiOauthVerifier;
import org.imsglobal.lti.launch.LtiVerificationException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rmartin.lti.api.exception.ActivityInsufficientPermissionException;
import rmartin.lti.api.exception.ActivityNotFoundException;
import rmartin.lti.api.exception.InvalidCredentialsException;
import rmartin.lti.api.exception.LTIException;
import rmartin.lti.api.model.LTILaunchRequest;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.api.service.ConfigService;
import rmartin.lti.api.service.ContextService;
import rmartin.lti.api.service.Redis;
import rmartin.lti.server.service.*;
import rmartin.lti.server.service.impls.ActivityProviderServiceImpl;
import rmartin.lti.server.service.impls.ContextServiceImpl;
import rmartin.lti.server.service.impls.GradeServiceImpl;
import rmartin.lti.server.service.impls.KeyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/lti")
public class LTIController {

    private static final Logger log = Logger.getLogger(ActivityProviderServiceImpl.class);

    private final KeyService keyService;

    private final GradeService gradeService;

    private final ContextService contextService;

    private final ConfigService configService;

    private final ActivityProviderService activityProviderService;

    private final Redis redis;

    private final ObjectMapper mapper;

    private final RequestValidator validator;

    @Autowired
    public LTIController(KeyServiceImpl keyService, GradeServiceImpl gradeService, ContextServiceImpl launchService, ConfigService configService, ActivityProviderService activityProviderService, Redis redis, ObjectMapper mapper, RequestValidator validator) {
        this.keyService = keyService;
        this.gradeService = gradeService;
        this.contextService = launchService;
        this.configService = configService;
        this.activityProviderService = activityProviderService;
        this.redis = redis;
        this.mapper = mapper;
        this.validator = validator;
    }

    @PostMapping(value = "/test", consumes = {"application/x-www-form-urlencoded"})
    public String handleTestRequest(@RequestParam Map<String, String> launchParams, Model model, HttpServletRequest request) {

        LTILaunchRequest launchRequest = mapper.convertValue(launchParams, LTILaunchRequest.class);

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

    @PostMapping(value = "/launch/{activityName}", consumes = {"application/x-www-form-urlencoded"})
    //@LTISigned
    public String launchApp(HttpServletRequest request, @PathVariable String activityName) {
        Map<String, String[]> launchParams = request.getParameterMap();
        if(!validator.isValidRequest(request)){
            return "redirect:/invalid-lti";
        }
        Map<String, String> toPojo = new HashMap<>();
        Map<String, String> customParams = new HashMap<>();

        launchParams.forEach((k, v) -> {
            if(v.length != 1) {
                throw new UnsupportedOperationException(String.format("Key %s has multiple values %s", k, Arrays.toString(v)));
            }
            if(k.startsWith("custom")){
                customParams.put(k, v[0]);
            } else {
                toPojo.put(k, v[0]);
            }
        });

        LTILaunchRequest launchRequest = mapper.convertValue(toPojo, LTILaunchRequest.class);
        launchRequest.setCustomParams(customParams);
        launchRequest.validate();

        // Check that the activity exists, and has permission to launch it
        var potentialActivity = activityProviderService.getActivityByName(activityName);
        if(potentialActivity.isEmpty()){
            throw new ActivityNotFoundException("Activity: " + activityName + "does not exist");
        }

        // todo limpiar excepciones y todas las que esten relacionadas con LTI lanzarlas a traves de ella.
        // Revisar docs de spring formas recomendadas de manejo de errores/excepciones
        var activity = potentialActivity.get();
        if (!this.activityProviderService.canLaunch(launchRequest.getOauthConsumerKey(), activity)) {
            throw new ActivityInsufficientPermissionException("Insufficient permissions");
        }

        if(activity.getUrl() == null || activity.getUrl().trim().isEmpty()){
            throw new LTIException("Activity exists, but URL is not configured yet");
        }

        log.info("Launch request successfully validated, generating context and pushing data");

        // We have the data, is it from an existing user? is it the first visit?
        LTIContext context = contextService.getOrInitialize(launchRequest, activityName);

        // Context does not store activity configuration in DB, configuration is delegated to the appropriate service
        configService.calculateConfig(context);

        // Push data to Redis
        String publicId = launchRequest.getPublicId();
        redis.saveForLaunch(context, publicId);

        // Trigger activiy launch
        return "redirect:"+activity.getUrl()+"/"+ publicId;
    }

}
