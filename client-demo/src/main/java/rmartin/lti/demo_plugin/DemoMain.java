package rmartin.lti.demo_plugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


// We need to exclude the DataSourceAutoConfiguration.class because we do not have a valid database configuration
// It should be removed if we include any datasource dependency inside the pom.xml, such as h2, mysql, mongo, etc
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})

// Scan all entities and components that are inside any of the following packages
@ComponentScan(basePackages = {"rmartin.lti"})
@EntityScan(basePackages = {"rmartin.lti"})
public class DemoMain {
	public static void main(String[] args) {
		SpringApplication.run(DemoMain.class, args);
	}
}