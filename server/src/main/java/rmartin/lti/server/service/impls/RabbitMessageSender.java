package rmartin.lti.server.service.impls;

import rmartin.lti.api.model.LTIScoreRequest;
import rmartin.lti.api.service.AsyncMessageSender;
import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageSender {

    private static final Logger logger = Logger.getLogger(AsyncMessageSender.class);

    private final RabbitTemplate template;

    @Value("${server.amqp.exchange}")
    private String exchange;

    @Value("${server.amqp.scoresKey}")
    private String routingKey;

    @Autowired
    public RabbitMessageSender(RabbitTemplate template) {
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        this.template = template;
    }

    public void send(LTIScoreRequest message){
        logger.info("Sending message + "+message);
        this.template.convertAndSend(exchange, routingKey, message);
    }
}
