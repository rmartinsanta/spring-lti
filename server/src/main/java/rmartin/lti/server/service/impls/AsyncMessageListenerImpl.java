package rmartin.lti.server.service.impls;

import rmartin.lti.server.model.MessageDTO;
import rmartin.lti.server.service.AsyncMessageListener;
import rmartin.lti.server.service.GradeService;
import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncMessageListenerImpl implements AsyncMessageListener {

    private Logger logger = Logger.getLogger(AsyncMessageListenerImpl.class);

    private final GradeService gradeService;

    @Autowired
    public AsyncMessageListenerImpl(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @Override
    @RabbitListener(queues = "pendingscores")
    public void processMessage(MessageDTO message) {

        logger.info("Recieved message: "+message);
        // MessageDTO can hav ea type, we can launch different actions depending on the message type blabla

        if(message.getScore() < 0 || message.getScore() > 1)
            // Or send to error queue instead of triggering exception
            throw new IllegalArgumentException();

        gradeService.gradeActivity(message.getContext(), Double.toString(message.getScore()));
    }
}
