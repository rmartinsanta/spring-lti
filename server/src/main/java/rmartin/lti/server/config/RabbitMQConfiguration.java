package rmartin.lti.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitMQConfiguration {

    @Value("${server.amqp.queue:'default-name'}")
    private String QUEUE_NAME;
    private static final boolean QUEUE_DURABLE = true;
    @Value("${server.amqp.scoresKey:'score.#'}")
    private String SCORES_ROUTING_KEY;

    @Value("${server.amqp.exchange}")
    private String EXCHANGE_NAME;

    @Value("${server.amqp.errorqueue}")
    private String ERROR_QUEUE;

    private static final boolean EXCHANGE_DURABLE = true;
    private static final boolean EXCHANGE_AUTODELETE = false;

    @Bean
    Queue incomingQueue(){
        //return new Queue(QUEUE_NAME, QUEUE_DURABLE);

        QueueBuilder queueBuilder = QUEUE_DURABLE? QueueBuilder.durable(QUEUE_NAME): QueueBuilder.nonDurable(QUEUE_NAME);

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange","" );
        // All queues are automatically binded to the default no name exchange, using their queue name as routing key.
        arguments.put("x-dead-letter-routing-key", ERROR_QUEUE);

        return queueBuilder.withArguments(arguments).build();
    }

    @Bean
    Queue errorQueue(){
        return QueueBuilder.durable(ERROR_QUEUE).build();
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME, EXCHANGE_DURABLE, EXCHANGE_AUTODELETE);
    }

    @Bean
    Binding binding(TopicExchange exchange){
        return BindingBuilder.bind(incomingQueue()).to(exchange).with(SCORES_ROUTING_KEY);
    }

    @Bean
    RabbitTemplate listenerContainer(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2MessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(mapper);
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        return converter;
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        factory.setMessageConverter(jackson2MessageConverter());
        return factory;
    }

}
