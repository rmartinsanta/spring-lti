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
import rmartin.lti.demo_plugin.model.CTFdService;
import rmartin.lti.demo_plugin.services.ContextService;
import rmartin.lti.demo_plugin.services.GradeService;

@Controller
public class TestActivityController {

    public static final String LAUNCH_URL = "/proxy/";
    private static final Logger log  = Logger.getLogger(TestActivityController.class);

    private final ContextService contextService;
    private final GradeService gradeService;

    private final CTFdService ctfdService;

    @Value("${lti.activity.debug}")
    private boolean debug;

    @Value("${ctfd.loginurl}")
    private String loginurl;

    @Autowired
    public TestActivityController(ContextService contextService, GradeService gradeService, CTFdService ctfdService) {
        this.contextService = contextService;
        this.gradeService = gradeService;
        this.ctfdService = ctfdService;
    }

    /**
     * This method will be called when the activity is launched.
     * The LTI proxy will redirect the user to this endpoint, as configured in the app.properties
     */
    @GetMapping(LAUNCH_URL + "{id}")
    public ModelAndView handleActivityLaunch(ModelAndView modelView, @PathVariable String id){
        // Retrieve the current context
        LTIContext context = contextService.initialize(id);

        var lastRequest = context.getLastRequest();
        var user = ctfdService.searchOrRegister(lastRequest.getPersonEmail(), lastRequest.getPersonFullName());


        modelView.addObject("loginurl", loginurl);
        modelView.addObject("name", user.getEmail());
        modelView.addObject("password", user.getPassword());

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

}

