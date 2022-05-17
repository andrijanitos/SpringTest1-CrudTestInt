package com.andrijanica.SpringTest1.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Represents security configuration.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                .and()
                .csrf().disable()
                .authorizeRequests().antMatchers().permitAll()
                .anyRequest().permitAll();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .antMatchers("/resources/",
                        "/actuator/",
                        "/static/",
                        "/webjars/",
                        "/v3/api-docs/**",
                        "/configuration/ui",
                        "/swagger-resources/",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/",
                        "/h2-console/**",
                        "/swagger-ui/**");
    }
}
