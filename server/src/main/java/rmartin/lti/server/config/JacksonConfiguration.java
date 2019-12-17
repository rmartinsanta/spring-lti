package rmartin.lti.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import rmartin.lti.server.model.LaunchContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2JsonRedisSerializer launchRequestSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Jackson2JsonRedisSerializer<LaunchContext> serializer = new Jackson2JsonRedisSerializer<>(LaunchContext.class);
        serializer.setObjectMapper(mapper);
        return serializer;
    }
}
