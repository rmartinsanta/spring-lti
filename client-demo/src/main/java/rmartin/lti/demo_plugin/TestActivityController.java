package rmartin.lti.demo_plugin;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rmartin.lti.server.model.Activity;
import rmartin.lti.server.model.ActivityConfig;
import rmartin.lti.server.model.LTIContext;
import rmartin.lti.server.model.enums.ConfigKeys;
import rmartin.lti.server.service.ConfigService;
import rmartin.lti.server.service.ContextService;
import rmartin.lti.server.service.IOUtils;

import static rmartin.lti.demo_plugin.TestActivityController.ACTIVITY_ID;

@Controller
@RequestMapping("/"+ACTIVITY_ID)
public class TestActivityController extends Activity {

    public static final String ACTIVITY_ID = "test";
    private static final Logger log  = Logger.getLogger(TestActivityController.class);

    private final ContextService contextService;
    private final ConfigService configService;

    @Autowired
    public TestActivityController(ContextService contextService, ConfigService configService) {
        this.contextService = contextService;
        this.configService = configService;
    }

    @Override
    public String getactivityId() {
        return ACTIVITY_ID;
    }

    @GetMapping("/{id}")
    public ModelAndView handleActivityLaunch(ModelAndView modelView, @PathVariable String id){

        LTIContext context = this.getContext(id);
        modelView.setViewName("TestActivity/index");

        modelView.addObject("canSubmit", !cannotRetry(context));
        modelView.addObject("canSubmit", context.getConfig().getBool(ConfigKeys.CAN_RETRY));
        modelView.addObject("postUrl", "/"+ACTIVITY_ID+"/end");

        modelView.addObject("c", context);

        if(isDebugEnabled()){
            modelView.addObject("debug", IOUtils.object2Map(context));
            modelView.addObject("isdebug", true);

            modelView.addObject("requests", context.getLaunchRequests());
        }

        // After the launch, we consumed the context data from Redis.
        // We can reuse Redis to store in use contexts, BUT under a different key only known by us and the current view

        String secretKey = contextService.store(context);

        modelView.addObject("secretKey", secretKey);

        return modelView;
    }

    @PostMapping("/end")
    public String endActivity(@RequestParam float score, @RequestParam String secretKey){
        if(score < 0 || score > 1){
            return "ScoreMustBeBetween0And1";
        }

        LTIContext context = this.contextService.get(secretKey);
        if(context == null){
            return "KeyDoesNotExistOrHasAlreadyBeenUsed";
        }


//        if(!context.getConfig().getBool(DemoConfig.CAN_RETRY) && !context.getResults().isEmpty()){
//            throw new GradeException("Retry is disabled and a result has already been submitted");
//        }

        this.grade(context, score);
        // Grade the activity, and return control to LMS
        return "redirect:"+context.getLastRequest().getReturnUrl();
    }

    @PostMapping("/allowRetry")
    public ResponseEntity setAllowRetry(boolean allowRetry, String secretKey){

        log.info("Change in TestActivity Config -- Set AllowRetry to "+allowRetry);
        ActivityConfig config = contextService.get(secretKey).getConfig();
        config.setValue(ConfigKeys.CAN_RETRY, allowRetry);
        configService.save(config);
        return ResponseEntity.ok().build();
    }
}

