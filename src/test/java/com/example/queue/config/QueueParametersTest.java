package com.example.queue.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QueueParametersTest {

    @Autowired QueueParameters queueParameters;

    @Test
    void timeForOrder() {
        queueParameters.timeForOrder(1000);
        assertThat(queueParameters.timeForOrder()).isEqualTo(1000L);
    }

    @Test
    void openHour() {
        queueParameters.openHour(1000);
        assertThat(queueParameters.openHour()).isEqualTo(1000L);
    }

    @Test
    void openMinutes() {
        queueParameters.openMinutes(1000);
        assertThat(queueParameters.openMinutes()).isEqualTo(1000L);
    }

    @Test
    void closingHour() {
        queueParameters.closingHour(1000);
        assertThat(queueParameters.closingHour()).isEqualTo(1000L);
    }

    @Test
    void closingMinute() {
        queueParameters.closingMinute(1000);
        assertThat(queueParameters.closingMinute()).isEqualTo(1000L);
    }

    @Test
    void millisToConfirm() {
        queueParameters.millisToConfirm(1000);
        assertThat(queueParameters.millisToConfirm()).isEqualTo(1000L);
    }
}