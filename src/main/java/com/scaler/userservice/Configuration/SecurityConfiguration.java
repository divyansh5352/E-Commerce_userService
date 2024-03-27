package com.scaler.userservice.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // this is done to say , hey spring allow all request , don't put permission on anything

        /*
        by default spring security makes any request on authenticated
        http.authorizeHttpRequests((requests) -> requests
                .anyRequest().authenticated()
        );
         */
        http.authorizeHttpRequests((requests) -> {
                    try {
                        requests
                                .anyRequest().permitAll()
                                .and().cors().disable()
                                .csrf().disable();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return http.build();
    }
}
