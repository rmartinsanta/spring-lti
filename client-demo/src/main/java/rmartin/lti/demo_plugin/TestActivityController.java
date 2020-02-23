package rmartin.lti.demo_plugin;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import rmartin.lti.api.model.ActivityConfig;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.api.service.IOUtils;
import rmartin.lti.demo_plugin.services.ContextService;
import rmartin.lti.demo_plugin.services.GradeService;

@Controller
public class TestActivityController {

    public static final String LAUNCH_URL = "/lti/start/";
    private static final Logger log  = Logger.getLogger(TestActivityController.class);

    private final ContextService contextService;
    private final GradeService gradeService;

    @Value("${lti.activity.debug}")
    private boolean debug;

    @Autowired
    public TestActivityController(ContextService contextService, GradeService gradeService) {
        this.contextService = contextService;
        this.gradeService = gradeService;
    }

    /**
     * This method will be called when the activity is launched.
     * The LTI proxy will redirect the user to this endpoint, as configured in the app.properties
     */
    @GetMapping(LAUNCH_URL + "{id}")
    public ModelAndView handleActivityLaunch(ModelAndView modelView, @PathVariable String id){
        // Retrieve the current context
        LTIContext context = contextService.getContext(id);

        // We have not made any changes, but we want to access the context later, save it.
        String key = contextService.storeContext(context);
        modelView.addObject("secretKey", key);
        modelView.addObject("canSubmit", this.gradeService.canSubmitScore(context));
        modelView.addObject("c", context);
        modelView.addObject("retryAllowed", context.getConfig().getValue(ConfigKeys.CAN_RETRY, false));

        // Add some debug information if debug is enabled (in app.properties)
        if(debug){
            modelView.addObject("debug", IOUtils.object2Map(context));
            modelView.addObject("isdebug", true);
            modelView.addObject("requests", context.getLaunchRequests());
        }

        // Render the index.mustache file
        modelView.setViewName("index");
        return modelView;
    }

    /**
     * End activity and return control to the LMS
     * @param score
     * @param secretKey
     * @return
     */
    @PostMapping("/end")
    public String endActivity(@RequestParam float score, @RequestParam String secretKey){

        LTIContext context = this.contextService.getContext(secretKey);

        // Submit grade request
        this.gradeService.grade(context, score);

        // Redirect back to the LMS
        return "redirect:" + context.getLastRequest().getReturnUrl();
    }

    /**
     * Update configuration and return control to the LMS
     * @param allowRetry
     * @param secretKey
     * @return
     */
    @PostMapping("/updateConfig")
    public String setAllowRetry(@RequestParam boolean allowRetry, @RequestParam String secretKey){
        log.info("Change in TestActivity Config -- Set AllowRetry to "+allowRetry);
        LTIContext context = contextService.getContext(secretKey);
        if(!context.isPrivileged()){
            throw new AccessDeniedException("Only teachers or admins can update the activity config");
        }
        ActivityConfig config = context.getConfig();
        config.setValue(ConfigKeys.CAN_RETRY, allowRetry);
        String newKey = contextService.storeContext(context, true);
        //return "redirect:" + LAUNCH_URL + newKey;

        // Redirect back to the LMS after the config has been successfully updated
        return "redirect:" +context.getLastRequest().getReturnUrl();
    }
}

