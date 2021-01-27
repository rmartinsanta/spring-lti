package rmartin.lti.server.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;
import rmartin.lti.server.service.ActivityProviderService;

import java.util.Collections;

import static rmartin.lti.server.shell.ShellUtils.*;

@ShellComponent
public class ActivityProviderCommands {

    private final ActivityProviderService activityProviderService;

    public ActivityProviderCommands(ActivityProviderService activityProviderService) {
        this.activityProviderService = activityProviderService;
    }

    @ShellMethod(key = "activity add", value = "Create and activate a new activity provider")
    public String addActivity(String name){
        var activityProvider = activityProviderService.createActivityProvider(name);
        return String.format("Activity %s created, with secret key '%s'", activityProvider.getName(), activityProvider.getSecret());
    }

    @ShellMethod(key = "activity remove", value = "Delete an activity provider")
    public String removeConsumer(String username){
        var deleted = activityProviderService.removeActivity(username);
        return String.format("Consumer %s deleted", deleted.getName());
    }

    @ShellMethod(key = "activity get", value = "Find an activity provider given its name")
    public Object findConsumer(String username){
        var activity = activityProviderService.getActivityByName(username);
        if(!activity.isPresent()){
            return error("Could not find any activity provider with name '%s'", username);
        }
        var data = Collections.singleton(activity.get());
        return getActivitiesAsTable(data);
    }

    @ShellMethod(key = "activity list", value = "List all registered activity providers")
    public Table listConsumers(){
        var consumers = activityProviderService.findAll();
        return getActivitiesAsTable(consumers);
    }

}
