package com.example.queue.services;

import com.example.queue.Constants;
import com.example.queue.TestEntities;
import com.example.queue.entities.Booking;
import com.example.queue.entities.BookingTime;
import com.example.queue.entities.User;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MainServiceTest {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 60000);
    TestEntities testEntities = new TestEntities();
    BookingTime bookingTime = testEntities.getBookingTime();
    User user = testEntities.testUser();
    Booking booking = testEntities.testBooking(timestamp, user);

    @Autowired
    MainService mainService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    CalendarService calendarService;

    @BeforeEach
    void setUp(){
        // hour and minutes = 0  - ищем за весь день

        bookingTime.setHour(0);
        bookingTime.setMinute(0);

        userRepo.save(user);
        bookingRepo.save(booking);
    }

    @Test
    void getThisDayBookings_QUEUE_IS_FREE() {
        // смотрим следующий день
        bookingTime.setDay(bookingTime.getDay() + 1);
        String bookings = mainService.getThisDayBookings(bookingTime);
        assertThat(bookings).isEqualTo(Constants.QUEUE_IS_FREE);
    }

    @Test
    void getThisDayBookings_RETURNS_TIMESTAMPS(){
        String bookings = mainService.getThisDayBookings(bookingTime);
        assertThat(bookings.substring(1, bookings.length() - 1)).isEqualTo(booking.bookingTime().toString());
    }

    @AfterEach
    void tearDown(){
        bookingRepo.delete(booking);
        userRepo.delete(user);
    }

}