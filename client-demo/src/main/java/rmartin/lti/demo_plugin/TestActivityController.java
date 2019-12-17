package rmartin.lti.demo_plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import rmartin.lti.server.model.Activity;
import rmartin.lti.server.model.ActivityConfig;
import rmartin.lti.server.model.LaunchContext;
import rmartin.lti.server.service.AsyncMessageSender;
import rmartin.lti.server.service.ConfigService;
import rmartin.lti.server.service.ContextService;
import rmartin.lti.server.service.Redis;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static rmartin.lti.demo_plugin.TestActivityController.ACTIVITY_ID;

@Controller
@RequestMapping("/"+ACTIVITY_ID)
public class TestActivityController extends Activity {

    private static final Logger log  = Logger.getLogger(TestActivityController.class);

    @Value("${server.context.debug}")
    private boolean debugEnabled;

    static final String ACTIVITY_ID = "test";

    private final ContextService contextService;

    private final ConfigService configService;

    @Autowired
    public TestActivityController(Redis redis, AsyncMessageSender messageSender, ContextService contextService, ConfigService configService) {
        super(redis, messageSender);
        this.contextService = contextService;
        this.configService = configService;
    }

    @Override
    public String getactivityId() {
        return ACTIVITY_ID;
    }

    @GetMapping("/{id}")
    public ModelAndView handleActivityLaunch(ModelAndView modelView, @PathVariable String id){

        LaunchContext context = this.getLaunchContext(id);
        modelView.setViewName("TestActivity/index");

        modelView.addObject("canSubmit", !cannotRetry(context));
        modelView.addObject("canSubmit", context.getConfig().getBool(DemoConfig.CAN_RETRY));
        modelView.addObject("postUrl", "/"+ACTIVITY_ID+"/end");

        modelView.addObject("c", context);

        if(debugEnabled){
            modelView.addObject("debug", dtoToArray(context));
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

        LaunchContext context = this.contextService.get(secretKey);
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
        config.setValue(DemoConfig.CAN_RETRY, allowRetry);
        configService.save(config);
        return ResponseEntity.ok().build();
    }

    private Map.Entry<String, Object>[] dtoToArray(Object any){
        // DTO to pairs of property name - property value
        Map.Entry<String, Object>[] data = (Map.Entry<String, Object>[]) new ObjectMapper().convertValue(any, HashMap.class)
                .entrySet()
                .stream()
                .filter(e -> !((Map.Entry) e).getKey().equals("launchRequests"))
                .toArray(Map.Entry[]::new);
        // Sort alphabetically
        Arrays.sort(data, Comparator.comparing(Map.Entry::getKey));
        return data;
    }
}

