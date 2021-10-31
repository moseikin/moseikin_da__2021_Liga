package com.example.queue.config;

import com.example.queue.services.EmailService;
import com.example.queue.services.interfaces.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@PropertySource(value = {"classpath:rabbitmq.properties"})
public class SpringConfig {


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Notification notification(){
        return new EmailService();
    }

}
