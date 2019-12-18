package rmartin.lti.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import rmartin.lti.server.model.LTIContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2JsonRedisSerializer<LTIContext> launchRequestSerializer() {
        Jackson2JsonRedisSerializer<LTIContext> serializer = new Jackson2JsonRedisSerializer<>(LTIContext.class);
        serializer.setObjectMapper(getObjectMapper());
        return serializer;
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
