package rmartin.lti.demo_plugin.lti_api;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmartin.lti.api.exception.GradeException;
import rmartin.lti.api.model.LTIContext;
import rmartin.lti.api.model.LTIScoreRequest;
import rmartin.lti.api.model.LTIScoreResponse;
import rmartin.lti.api.model.enums.ContextStatus;
import rmartin.lti.demo_plugin.ConfigKeys;

@Service
public class GradeService {

    private static final Logger logger = Logger.getLogger(GradeService.class);

    @Autowired
    APIClient client;

    public LTIScoreResponse grade(LTIContext context, float score){
        if(canSubmitScore(context)){
            throw new GradeException("Activity does not allow retrying and user has already been graded, context id: "+context.getId());
        }

        if(score < 0 || score > 1){
            throw new GradeException("Invalid score: must be a number between 0 and 1 (inclusive)");
        }

        logger.info(String.format("Submitting grade request: Score [%s], activity [%s] user [%s] ", score, context.getResourceId(), context.getUserId()));
        return this.client.submitGradeRequest(new LTIScoreRequest(context, score));
    }

    /**
     * Check if submitting scores for the given context is allowed
     * @param context Context to check
     * @return false if the user can retry, true if the user can not retry
     */
    public boolean canSubmitScore(LTIContext context) {
        return context.getStatus() != ContextStatus.SCORE_SUBMITTED || context.getConfig().getBool(ConfigKeys.CAN_RETRY, true);
    }

}
