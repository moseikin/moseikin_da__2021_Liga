package com.example.queue.services;

import com.example.queue.entities.BookingTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class MainServiceTest {

    @Autowired
    MainService mainService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getThisDayBookings() {
        BookingTime bookingTime = new BookingTime();
//        bookingTime.
//        mainService.getThisDayBookings();
    }
}