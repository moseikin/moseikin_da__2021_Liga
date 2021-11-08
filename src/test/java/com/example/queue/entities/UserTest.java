package com.example.queue.entities;

import com.example.queue.TestEntities;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import com.example.queue.services.CalendarService;
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
class UserTest {
    private final TestEntities testEntities = new TestEntities();
    private User user;
    private final BookingTime bookingTime = testEntities.getBookingTime();
    private Booking booking;

    @Autowired UserRepo userRepo;
    @Autowired BookingRepo bookingRepo;
    @Autowired CalendarService calendarService;

    @BeforeEach
    void setUp(){
        user = testEntities.testUser();
        userRepo.save(user);
        Timestamp timestamp = calendarService.bookingTimeToTimestamp(bookingTime);
        booking = testEntities.testBooking(timestamp, user);
        bookingRepo.save(booking);
    }

    @AfterEach
    void tearDown(){
        bookingRepo.delete(booking);
        userRepo.delete(user);
    }


    @Test
    void testToString() {
        String expected = "\n id=" + user.id() +
                ", login='" + user.login() +
                ", name='" + user.name() +
                ", lastName='" + user.lastName() +
                ", role='" + user.role() +
                ", eMail='" + user.eMail();
        assertThat(user.toString()).isEqualTo(expected);
    }

    @Test
    void testHashCode() {
        User gotFromDb = userRepo.getUserByLogin(user.login());
        assertThat(user.hashCode()).isEqualTo(gotFromDb.hashCode());
    }

    @Test
    void testEquals(){
        User gotFromDb = userRepo.getUserByLogin(user.login());
        assertThat(user.equals(gotFromDb)).isTrue();
    }

}