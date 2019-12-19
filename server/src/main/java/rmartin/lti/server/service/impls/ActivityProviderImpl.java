package rmartin.lti.server.service.impls;

import rmartin.lti.server.service.ActivityProvider;
import rmartin.lti.api.model.Activity;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityProviderImpl implements ActivityProvider {

    private final Map<String, Activity> activities = new HashMap<>();

    private static final Logger log = Logger.getLogger(ActivityProviderImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public ActivityProviderImpl(List<Activity> activitiesFound) {
        log.info(String.format("Available activities: %s", activitiesFound.stream().map(Activity::getactivityId).reduce("", (p, s)-> s + " " + p)));
        for (Activity activity : activitiesFound) {
            if(this.activities.containsKey(activity.getactivityId())) {
                throw new IllegalArgumentException("Two activities cannot share the same id: "+activity.getactivityId());
            }
            this.activities.put(activity.getactivityId(), activity);
        }
    }

    @Override
    public Activity getActivity(String id){
        return this.activities.get(id);
    }

    @Override
    public boolean canLaunch(String id) {
        return exists(id);
    }

    @Override
    public boolean exists(String id) {
        return this.activities.containsKey(id);
    }
}
