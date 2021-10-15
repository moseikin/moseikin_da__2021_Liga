package com.example.liquibasedemo.config;

import com.example.liquibasedemo.service.BasicService;
import com.example.liquibasedemo.service.DtoConverterService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class SpringConfig {


    @Bean
    public DtoConverterService dtoConverterService(){
        return new DtoConverterService();
    }

    @Bean
    public BasicService basicService(){
        return new BasicService();
    }

}
