package rmartin.lti.server.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import rmartin.lti.api.exception.GradeException;
import rmartin.lti.server.model.enums.ContextStatus;
import rmartin.lti.server.service.AsyncMessageSender;
import rmartin.lti.server.service.Redis;

public abstract class Activity {

    @Autowired
    private Redis puller;

    @Autowired
    private AsyncMessageSender messageSender;

    @Value("${server.context.debug}")
    private boolean debugEnabled;

    /**
     * Get a unique string representing this activity name
     * @return A string representing the activity ID
     */
    public abstract String getactivityId();

    // TODO review & refactor this, use service?
    protected LTIContext getLaunchContext(String id) {
        LTIContext context = puller.getDataLaunch(id);
        return context;
    }

    protected void grade(LTIContext context, float score){
        if(cannotRetry(context)){
            throw new GradeException("Activity does not allow retrying and user has already been graded, context id: "+context.getId());
        }

        if(score < 0 || score > 1){
            throw new GradeException("Invalid score: must be a number between 0 and 1 (inclusive)");
        }

        this.messageSender.send(new MessageDTO(context, score));
    }

    /**
     * Check if submitting scores for the given context is allowed
     * @param context Context to check
     * @return false if the user can retry, true if the user can not retry
     */
    protected boolean cannotRetry(LTIContext context) {
        return context.getStatus() == ContextStatus.SCORE_SUBMITTED && !context.getConfig().getBool(BaseConfig.CAN_RETRY);
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }
}
