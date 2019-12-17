package rmartin.lti.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"rmartin.lti"})
public class LtiIntegrationServer {
	public static void main(String[] args) {
		SpringApplication.run(LtiIntegrationServer.class, args);
	}
}