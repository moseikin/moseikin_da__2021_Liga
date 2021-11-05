package com.example.queue.services;

import com.example.queue.Constants;
import com.example.queue.TestEntities;
import com.example.queue.config.JwtFilter;
import com.example.queue.config.JwtProvider;
import com.example.queue.entities.Booking;
import com.example.queue.entities.BookingTime;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.RolesEnum;
import com.example.queue.entities.enums.StatusesEnum;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AdminServiceTest {
    TestEntities testEntities = new TestEntities();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    User user = testEntities.testUser();
    User admin;
    Booking booking;
    Authentication auth;
    String newBooking;
    BookingTime bookingTime;


    @Autowired AdminService adminService;
    @Autowired BookingService bookingService;
    @Autowired BookingRepo bookingRepo;
    @Autowired UserRepo userRepo;
    @Autowired JwtFilter jwtFilter;
    @Autowired JwtProvider jwtProvider;
    @Autowired CalendarService calendarService;

    @BeforeEach
    void setUp(){
        userRepo.save(user);
        booking = testEntities.testBooking(timestamp, user);
//        bookingRepo.save(booking);
        admin = adminService.addAdmin();
        initAuth(admin.login());
        bookingTime = testEntities.getBookingTime();
    }

    @Test
    void addAdmin() {
        assertThat(admin.role()).isEqualTo(RolesEnum.ADMIN.getRole());
    }

    @Test
    void setAppeared() {
        addTenMinutes();
        initBooking();
        String status = adminService.setAppeared(booking.bookId());
        assertThat(status).isEqualTo(Constants.APPEARED);
    }

    @Test
    void setAppeared_ExpectCannotFindBooking() {
        String status = adminService.setAppeared(0);
        assertThat(status).isEqualTo(Constants.CANNOT_FIND_BOOKING);
    }

    @Test
    void setCompleted() {
        addTenMinutes();
        initBooking();
        String status = adminService.setCompleted(booking.bookId());
        assertThat(status).isEqualTo(Constants.COMPLETED);
    }

    @Test
    void setCompleted_ExpectCannotFindBooking() {
        String status = adminService.setCompleted(0);
        assertThat(status).isEqualTo(Constants.CANNOT_FIND_BOOKING);
    }

    @Test
    void setAnnulled() {
        addTenMinutes();
        initBooking();
        String status = adminService.setAnnulled(booking.bookId());
        assertThat(status).isEqualTo(Constants.ANNULLED);
    }

    @Test
    void setAnnulled_ExpectCannotFindBooking() {
        String status = adminService.setAnnulled(0);
        assertThat(status).isEqualTo(Constants.CANNOT_FIND_BOOKING);
    }

    void initAuth(String login){
        String token = jwtProvider.generateToken(login);
        jwtFilter.setAuthentication(token);
        auth = SecurityContextHolder.getContext().getAuthentication();
    }

    void initBooking(){
        newBooking = bookingService.createBooking(bookingTime, auth);
        timestamp = calendarService.bookingTimeToTimestamp(bookingTime);
        booking = testEntities.testBooking(timestamp, user);
        booking.bookId(ejectId(newBooking));
    }

    Long ejectId(String source) {
        int index = source.indexOf("=");
        int index2 = source.indexOf(",");
        return Long.parseLong(source.substring(index + 1, index2));
    }

    void addTenMinutes() {
        // add 10 minutes to sure book not in past
        bookingTime.setMinute(bookingTime.getMinute() + 10);
    }
}