package rmartin.lti.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"rmartin.lti"})
@EntityScan(basePackages = {"rmartin.lti"})
public class LtiProxyServer {
	public static void main(String[] args) {
		// Go Spring go
		SpringApplication.run(LtiProxyServer.class, args);
	}
}