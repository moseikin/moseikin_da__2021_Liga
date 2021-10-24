package com.example.queue.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@Setter
@Configuration
@PropertySource(value = {"classpath:queue.properties"})
public class QueueParameters {

    @Value("${timeForOrder}")
    private long timeForOrder;

    @Value("${openHour}")
    private int openHour;

    @Value("${openMinutes}")
    private int openMinutes;

    @Value("${closingHour}")
    private int closingHour;

    @Value("${closingMinute}")
    private int closingMinute;

    @Value("${millisToConfirm}")
    private int millisToConfirm;
}
