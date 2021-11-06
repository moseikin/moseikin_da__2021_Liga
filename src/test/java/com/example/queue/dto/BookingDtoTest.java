package com.example.queue.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingDtoTest {

    BookingDto bookingDto = new BookingDto();


    @Test
    void testBookingTime() {
        assertThat(bookingDto.bookingTime()).isNull();
        bookingDto.bookingTime(new Timestamp(System.currentTimeMillis()));
        assertThat(bookingDto.bookingTime()).isNotNull();
    }

    @Test
    void testName() {
        assertThat(bookingDto.name()).isNull();
        bookingDto.name("testName");
        assertThat(bookingDto.name()).isNotNull();
    }

    @Test
    void testLastName() {
        assertThat(bookingDto.lastName()).isNull();
        bookingDto.lastName("testName");
        assertThat(bookingDto.lastName()).isNotNull();
    }

    @Test
    void testEmail() {
        assertThat(bookingDto.email()).isNull();
        bookingDto.email("testEmail@test.test");
        assertThat(bookingDto.email()).isNotNull();
    }
}