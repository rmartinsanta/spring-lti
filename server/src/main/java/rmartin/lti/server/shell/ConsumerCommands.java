package rmartin.lti.server.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;
import rmartin.lti.server.service.ConsumerService;

import java.util.Collections;

import static rmartin.lti.server.shell.ShellUtils.error;
import static rmartin.lti.server.shell.ShellUtils.getConsumersAsTable;

@ShellComponent
public class ConsumerCommands {

    private final ConsumerService consumerService;

    public ConsumerCommands(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @ShellMethod(key = "consumer add", value = "Create and activate a new consumer")
    public String addConsumer(String name, String password){
        var consumer = consumerService.createConsumer(name, password);
        return String.format("Consumer %s created, with secret key '%s'", consumer.getUsername(), consumer.getSecret());
    }

    @ShellMethod(key = "consumer remove", value = "Destroy the consumer with the given username")
    public String removeConsumer(String username){
        var deleted = consumerService.deleteConsumer(username);
        return String.format("Consumer %s deleted", deleted.getUsername());
    }

    @ShellMethod(key = "consumer find", value = "Find a consumer with the given username")
    public Object findConsumer(String username){
        var consumer = consumerService.findByUsername(username);
        if(consumer.isEmpty()){
            return error("Could not find a customer with username '%s'", username);
        }
        var data = Collections.singleton(consumer.get());
        return getConsumersAsTable(data);
    }

    @ShellMethod(key = "consumer list", value = "List all registered customers")
    public Table listConsumers(){
        var consumers = consumerService.findAll();
        return getConsumersAsTable(consumers);
    }

}
