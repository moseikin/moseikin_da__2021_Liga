package com.example.queue.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "/queue-test.properties")
class CalendarServiceTest {

    @Value(value = "${openHour}")
    long openHour;
    @Value(value = "${openMinutes}")
    long openMinutes;
    @Value(value = "${closingHour}")
    long closingHour;
    @Value(value = "${closingMinute}")
    long closingMinutes;

    @Autowired CalendarService calendarService;

    @Test
    void calcWorkDayInMillis_WithCustomParams() {
        // test case when (closingMinutes - openMinutes) < 0
        long hours = closingHour - openHour;
        long minutes = closingMinutes - openMinutes;
        if (minutes < 0) {
            hours = hours -1;
            minutes = 60 + minutes;
        }
        long workDayInMillis = (hours * 3600000) + (minutes * 60000);

        assertThat(calendarService.calcWorkDayInMillis()).isEqualTo(workDayInMillis);


    }
}