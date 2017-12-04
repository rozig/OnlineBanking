package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {
    @Autowired
    Environment env;

    @Bean
    public String testBean() {
        String name = env.getProperty("company");
        System.out.println("Name -------------- : " + name);
        return name;
    }
}