package com.example.queue.services;

import com.example.queue.Constants;
import com.example.queue.TestEntities;
import com.example.queue.config.JwtFilter;
import com.example.queue.config.JwtProvider;
import com.example.queue.entities.Booking;
import com.example.queue.entities.BookingTime;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.RolesEnum;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@PropertySource(value = {"classpath:queue.properties"})
class BookingServiceTest {
    TestEntities testEntities = new TestEntities();
    User user;;
    Booking booking;
    Authentication auth;
    BookingTime bookingTime;

    @Value(value = "${closingHour}")
    int closingHour;

    @Value(value = "${timeForOrder}")
    int timeForOrder;

    @Autowired UserRepo userRepo;
    @Autowired BookingRepo bookingRepo;
    @Autowired BookingService bookingService;
    @Autowired JwtFilter jwtFilter;
    @Autowired JwtProvider jwtProvider;
    @Autowired CalendarService calendarService;

    @BeforeEach
    void setUp() {
        user = testEntities.testUser();
        userRepo.save(user);
        // do not save booking. It will be done in test

        String token = jwtProvider.generateToken(user.login());
        jwtFilter.setAuthentication(token);
        auth = SecurityContextHolder.getContext().getAuthentication();

        bookingTime = testEntities.getBookingTime();
    }

    @AfterEach
    void tearDown() {
        if (booking != null) {
            bookingRepo.delete(booking);
        }
        userRepo.delete(user);
    }

    @Test
    void createBooking() {
        // add 10 minutes to sure book not in past
        bookingTime.setMinute(bookingTime.getMinute() + 10);

        String newBooking = bookingService.createBooking(bookingTime, auth);
        int index = newBooking.indexOf("=");
        int index2 = newBooking.indexOf(",");
        String idString = newBooking.substring(index + 1, index2);

        Timestamp timestamp = calendarService.bookingTimeToTimestamp(bookingTime);
        booking = testEntities.testBooking(timestamp, user);
        booking.bookId(Long.parseLong(idString));

        assertThat(newBooking).isEqualTo(Constants.BOOKING_DONE + ": \n" + booking);
    }

    @Test
    void createBooking_ExpectThisDayGone() {
        // add 10 minutes to sure book is in past
        bookingTime.setMinute(bookingTime.getMinute() - 10);

        assertThat(bookingService.createBooking(bookingTime, auth)).isEqualTo(Constants.THIS_DAY_GONE);
    }

    @Test
    void createBooking_ExpectNotWorkingTime() {
        // add 1 hour to book to not working time
        bookingTime.setHour(closingHour + 1);

        assertThat(bookingService.createBooking(bookingTime, auth)).isEqualTo(Constants.NOT_WORKING_TIME);
    }

    @Test
    void createBooking_ExpectThisTimeOccupied() {
        // add 10 minutes to sure book not in past
        bookingTime.setMinute(bookingTime.getMinute() + 10);

        String newBooking = bookingService.createBooking(bookingTime, auth);
        int index = newBooking.indexOf("=");
        int index2 = newBooking.indexOf(",");
        String idString = newBooking.substring(index + 1, index2);

        Timestamp timestamp = calendarService.bookingTimeToTimestamp(bookingTime);
        booking = testEntities.testBooking(timestamp, user);
        booking.bookId(Long.parseLong(idString));

        // try to add new book half of timeForOrder later
        // convert to minutes
        timeForOrder = timeForOrder / 60000;
        bookingTime.setMinute(bookingTime.getMinute() + (timeForOrder / 2));

        assertThat(bookingService.createBooking(bookingTime, auth)).isEqualTo(Constants.THIS_TIME_OCCUPIED);
    }


//    @Test
//    void isBookingAvailable() {
//    }
//
//    @Test
//    void doAnnullingBook() {
//    }
//
    @Test
    void deleteBook() {

    }
//
//    @Test
//    void doRemoveBook() {
//    }
//
//    @Test
//    void getNearestBook() {
//    }
//
//    @Test
//    void doGetAllActiveBook() {
//    }
//
//    @Test
//    void getAllActiveBooks() {
//    }
//
    @Test
    void confirmBook() {
        // add 10 minutes to sure book not in past
        bookingTime.setMinute(bookingTime.getMinute() + 10);

        String newBooking = bookingService.createBooking(bookingTime, auth);
        int index = newBooking.indexOf("=");
        int index2 = newBooking.indexOf(",");
        String idString = newBooking.substring(index + 1, index2);

        Timestamp timestamp = calendarService.bookingTimeToTimestamp(bookingTime);
        booking = testEntities.testBooking(timestamp, user);
        booking.bookId(Long.parseLong(idString));

        String newStatus = bookingService.confirmBook(String.valueOf(user.id()), String.valueOf(booking.bookId()), auth.getName());

        assertThat(newStatus).isEqualTo(Constants.CONFIRMED);
    }

    @Test
    void confirmBook_ExpectCannotFindBooking() {
        // add 10 minutes to sure book not in past
        bookingTime.setMinute(bookingTime.getMinute() + 10);

        String newBooking = bookingService.createBooking(bookingTime, auth);
        int index = newBooking.indexOf("=");
        int index2 = newBooking.indexOf(",");
        String idString = newBooking.substring(index + 1, index2);

        Timestamp timestamp = calendarService.bookingTimeToTimestamp(bookingTime);
        booking = testEntities.testBooking(timestamp, user);
        booking.bookId(Long.parseLong(idString));

        String newStatus = bookingService.confirmBook(String.valueOf(user.id()), String.valueOf(0), auth.getName());

        assertThat(newStatus).isEqualTo(Constants.CANNOT_FIND_BOOKING);
    }

    @Test
    void confirmBook_ExpectLoginUntoSameUser() {
        // add 10 minutes to sure book not in past
        bookingTime.setMinute(bookingTime.getMinute() + 10);

        String newBooking = bookingService.createBooking(bookingTime, auth);
        int index = newBooking.indexOf("=");
        int index2 = newBooking.indexOf(",");
        String idString = newBooking.substring(index + 1, index2);

        Timestamp timestamp = calendarService.bookingTimeToTimestamp(bookingTime);
        booking = testEntities.testBooking(timestamp, user);
        booking.bookId(Long.parseLong(idString));

        User wrongUser = new User();
        wrongUser.login("wrong").pass("wrong").name("wrong").lastName("wrong").eMail("wrong@wrong.wrong").role(RolesEnum.USER.getRole());
        userRepo.save(wrongUser);

        String newStatus = bookingService.confirmBook(String.valueOf(wrongUser.id()), String.valueOf(booking.bookId()), auth.getName());

        assertThat(newStatus).isEqualTo(Constants.LOGIN_UNTO_SAME_USER);

        userRepo.delete(wrongUser);
    }
//
//    @Test
//    void setConfirmed() {
//    }
//
//    @Test
//    void getUserRepo() {
//    }
//
//    @Test
//    void getBookingRepo() {
//    }
//
//    @Test
//    void getScheduledService() {
//    }
//
//    @Test
//    void getAbility() {
//    }
//
//    @Test
//    void getNotification() {
//    }
//
//    @Test
//    void getMillisToConfirm() {
//    }
//
//    @Test
//    void setMillisToConfirm() {
//    }
}