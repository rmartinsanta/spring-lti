package rmartin.lti.demo_plugin;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Allow access to all URLs, we do not have anything to hide... yet.
        http.authorizeRequests().anyRequest().permitAll();

        // Conflicts with Mustache templates
        http.csrf().disable();

        // Need to disable frameOptions because we are going to be embedded in different LMSs
        http.headers().frameOptions().disable();
    }
}