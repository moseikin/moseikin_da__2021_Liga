package com.example.queue.controllers;

import com.example.queue.entities.BookingTime;
import com.example.queue.services.MainService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MainControllerTest {

    @Mock
    MainService mainService;
    @InjectMocks
    private MainController mainController;

    @LocalServerPort
    private int port;

//    @Autowired
//    private TestRestTemplate restTemplate;


    @Test
    void main(){
        assertThat(new TestRestTemplate().getForObject("http://localhost:" + port + "/", String.class))
                .contains();
    }

    @Test
    void contextLoads() {
        BookingTime bookingTime = new BookingTime();
        bookingTime.setYear(2021);
        bookingTime.setMonth(10);
        bookingTime.setDay(10);
        bookingTime.setHour(0);
        bookingTime.setMinute(0);
        when(mainService.getThisDayBookings(bookingTime)).thenReturn(String.valueOf(List.of()));

        mainController.getThisDayBookings(2021, 10, 10);

        verify(mainService).getThisDayBookings(bookingTime);
    }

    @Test
    void getThisDayBookings() {
        
    }
}