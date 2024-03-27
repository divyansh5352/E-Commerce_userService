package com.scaler.userservice.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(16);
    }
    //if we have to create custom beans that can be autowired at different places
    //we just creat them in the configuration class

    // if you annotate a method with bean ,
    // then that object can now to autowired anywhere across the application
}
