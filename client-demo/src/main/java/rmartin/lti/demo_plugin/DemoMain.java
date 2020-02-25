package rmartin.lti.demo_plugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

// If not using any database, exclude the DataSourceAutoConfiguration.class:

//@SpringBootApplication(exclude = {
//		DataSourceAutoConfiguration.class
//})

@SpringBootApplication

// Scan all entities and components that are inside any of the following packages
@ComponentScan(basePackages = {"rmartin.lti"})
@EntityScan(basePackages = {"rmartin.lti"})
public class DemoMain {
	public static void main(String[] args) {
		SpringApplication.run(DemoMain.class, args);
	}
}