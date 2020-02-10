package rmartin.lti.demo_plugin;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rmartin.lti.api.model.ActivityConfig;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.api.model.enums.ConfigKeys;
import rmartin.lti.api.service.IOUtils;
import rmartin.lti.demo_plugin.lti_api.ContextService;
import rmartin.lti.demo_plugin.lti_api.GradeService;

@Controller
public class TestActivityController {

    public static final String LAUNCH_URL = "/lti/start/";
    private static final Logger log  = Logger.getLogger(TestActivityController.class);

    private final ContextService contextService;
    private final GradeService gradeService;

    @Value("lti.activity.debug")
    private boolean debug;

    @Autowired
    public TestActivityController(ContextService contextService, GradeService gradeService) {
        this.contextService = contextService;
        this.gradeService = gradeService;
    }

    @GetMapping(LAUNCH_URL + "{id}")
    public ModelAndView handleActivityLaunch(ModelAndView modelView, @PathVariable String id){

        LTIContext context = contextService.getContext(id);
        modelView.setViewName("TestActivity/index");

        modelView.addObject("canSubmit", !this.gradeService.cannotRetry(context));
        modelView.addObject("canSubmit", context.getConfig().getBool(ConfigKeys.CAN_RETRY));
        modelView.addObject("postUrl", "/"+ LAUNCH_URL +"/end");
        modelView.addObject("c", context);

        if(debug){
            modelView.addObject("debug", IOUtils.object2Map(context));
            modelView.addObject("isdebug", true);
            modelView.addObject("requests", context.getLaunchRequests());
        }

        String secretKey = contextService.storeContext(context, false);
        modelView.addObject("secretKey", secretKey);

        return modelView;
    }

    @PostMapping("/end")
    public String endActivity(@RequestParam float score, @RequestParam String secretKey){
        if(score < 0 || score > 1){
            return "ScoreMustBeBetween0And1";
        }

        LTIContext context = this.contextService.getContext(secretKey);
        if(context == null){
            return "KeyDoesNotExistOrHasAlreadyBeenUsed";
        }

        this.gradeService.grade(context, score);
        // Grade the activity, and return control to LMS
        return "redirect:"+context.getLastRequest().getReturnUrl();
    }

    @PostMapping("/allowRetry")
    public ResponseEntity<?> setAllowRetry(boolean allowRetry, String secretKey){
        log.info("Change in TestActivity Config -- Set AllowRetry to "+allowRetry);
        LTIContext context = contextService.getContext(secretKey);
        ActivityConfig config = context.getConfig();
        config.setValue(ConfigKeys.CAN_RETRY, allowRetry);
        contextService.storeContext(context, true);
        return ResponseEntity.ok().build();
    }
}

