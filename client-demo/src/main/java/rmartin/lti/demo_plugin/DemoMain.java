package rmartin.lti.demo_plugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}) // Do not attempt to configure a database
@ComponentScan(basePackages = {"rmartin.lti"})
@EntityScan(basePackages = {"rmartin.lti"})
public class DemoMain {
	public static void main(String[] args) {
		// Go Spring go
		SpringApplication.run(DemoMain.class, args);
	}
}