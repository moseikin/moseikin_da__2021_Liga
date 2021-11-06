package com.example.queue.services;

import com.example.queue.CommonTestMethods;
import com.example.queue.Constants;
import com.example.queue.TestEntities;
import com.example.queue.config.JwtFilter;
import com.example.queue.config.JwtProvider;
import com.example.queue.dto.BookingDto;
import com.example.queue.entities.Booking;
import com.example.queue.entities.BookingTime;
import com.example.queue.entities.CustomUserDetails;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.RolesEnum;
import com.example.queue.entities.enums.StatusesEnum;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@PropertySource(value = {"classpath:queue.properties"})
class BookingServiceTest {
    CommonTestMethods methods = new CommonTestMethods();
    TestEntities testEntities = new TestEntities();
    User user;
    Booking booking;
    Authentication auth;
    BookingTime bookingTime;
    Timestamp timestamp;
    String newBooking;

    @Value(value = "${closingHour}")
    int closingHour;

    @Value(value = "${millisToConfirm}")
    Integer millisToConfirm;

    @Value(value = "${timeForOrder}")
    int timeForOrder;

    @Autowired UserRepo userRepo;
    @Autowired BookingRepo bookingRepo;
    @Autowired BookingService bookingService;
    @Autowired JwtFilter jwtFilter;
    @Autowired JwtProvider jwtProvider;
    @Autowired CalendarService calendarService;
    @Autowired AdminService adminService;
    @Autowired ScheduledService scheduledService;
    @Autowired CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        user = testEntities.testUser();
        userRepo.save(user);
        // do not save booking. It will be done in test

        initAuth(user.login());

        bookingTime = testEntities.getBookingTime();
    }

    void initBooking(){
        newBooking = bookingService.createBooking(bookingTime, auth);
        timestamp = calendarService.bookingTimeToTimestamp(bookingTime);
        booking = testEntities.testBooking(timestamp, user);
        booking.bookId(methods.ejectId(newBooking));
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
        addTenMinutes();
        initBooking();
        assertThat(newBooking).isEqualTo(Constants.BOOKING_DONE + ": \n" + booking);
    }

    @Test
    void createBooking_WithNotEmptyQueue() {
        addTenMinutes();
        initBooking();

        BookingTime bookingTime2 = bookingTime;
        bookingTime2.setMinute(bookingTime.getMinute() + 20);
        Booking booking2 = testEntities.testBooking(calendarService.bookingTimeToTimestamp(bookingTime2), user);
        String secondBook = bookingService.createBooking(bookingTime2, auth);
        booking2.bookId(methods.ejectId(secondBook));

        assertThat(secondBook).isEqualTo(Constants.BOOKING_DONE + ": \n" + booking2);
        bookingRepo.delete(booking2);
    }

    @Test
    void createBooking_ExpectUserNotFound() {
        putRoleDeletedToUser();
        assertThat(bookingService.createBooking(bookingTime, auth)).isEqualTo(Constants.USER_NOT_FOUND);
    }

    @Test
    void createBooking_ExpectThisDayGone() {
        // reduce 10 minutes to sure book is in past
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
        addTenMinutes();

        initBooking();

        // try to add new book half of timeForOrder later
        // convert to minutes
        timeForOrder = timeForOrder / 60000;
        bookingTime.setMinute(bookingTime.getMinute() + (timeForOrder / 2));

        assertThat(bookingService.createBooking(bookingTime, auth)).isEqualTo(Constants.THIS_TIME_OCCUPIED);
    }

    @Test
    void doAnnullingBook(){
        addTenMinutes();
        initBooking();
        bookingService.doAnnullingBook(booking.bookId());
        assertThat(bookingRepo.findByBookId(booking.bookId()).status())
                .isEqualTo(StatusesEnum.STATUS_ANNULLED.getStatus());
    }

    @Test
    void deleteBookAsUser() {
        addTenMinutes();
        initBooking();

        String result = bookingService.deleteBook(booking.bookId(), auth);

        assertThat(result).isEqualTo(Constants.REMOVING_SUCCEED);
    }

    @Test
    void deleteBookAsUser_ExpectUserNotFound() {
        putRoleDeletedToUser();
        assertThat(bookingService.deleteBook(1L, auth)).isEqualTo(Constants.USER_NOT_FOUND);
    }

    @Test
    void deleteBookAsAdmin() {
        addTenMinutes();
        initBooking();
        User admin = adminService.addAdmin();
        initAuth(admin.login());
        String result = bookingService.deleteBook(booking.bookId(), auth);

        assertThat(result).isEqualTo(Constants.REMOVING_SUCCEED);
    }

    @Test
    void deleteBook_ExpectCannotFindBooking() {
        addTenMinutes();
        initBooking();

        String result = bookingService.deleteBook(0L, auth);

        assertThat(result).isEqualTo(Constants.CANNOT_FIND_BOOKING);
    }

    @Test
    void getNearestBook() {
        addTenMinutes();
        initBooking();

        String result = bookingService.getNearestBook();
        assertThat(result).isEqualTo(new BookingDto().toBookingDto(booking).toString());
    }

    @Test
    void getNearestBook_ExpectCannotFindNearestActive() {
        String result = bookingService.getNearestBook();
        assertThat(result).isEqualTo(Constants.CANNOT_FIND_NEAREST_ACTIVE);
    }

    @Test
    void getAllActiveBooksAsUser() {
        addTenMinutes();
        initBooking();
        Pageable pageable = Pageable.ofSize(3);
        pageable.withPage(0);
        String result = bookingService.getAllActiveBooks(auth, pageable);
        assertThat(result.substring(1, result.length() - 1))
                .isEqualTo(new BookingDto().toBookingDto(booking).toString());
    }

    @Test
    void getAllActiveBooksAsUser_ExpectUserNotFound() {
        Pageable pageable = Pageable.ofSize(3);
        pageable.withPage(0);
        putRoleDeletedToUser();

        assertThat(bookingService.getAllActiveBooks(auth, pageable)).isEqualTo(Constants.USER_NOT_FOUND);
    }

    @Test
    void getAllActiveBooksAsAdmin() {
        addTenMinutes();
        initBooking();
        Pageable pageable = Pageable.ofSize(3);
        pageable.withPage(0);
        User admin = adminService.addAdmin();
        initAuth(admin.login());
        String result = bookingService.getAllActiveBooks(auth, pageable);
        assertThat(result.substring(1, result.length() - 1))
                .isEqualTo(new BookingDto().toBookingDto(booking).toString());
    }

    @Test
    void confirmBook() {
        addTenMinutes();
        initBooking();
        String newStatus = bookingService.confirmBook(String.valueOf(user.id()),
                                                        String.valueOf(booking.bookId()),
                                                        auth);
        assertThat(newStatus).isEqualTo(Constants.CONFIRMED);
    }

    @Test
    void confirmBook_ExpectUserNotFound() {
        putRoleDeletedToUser();
        assertThat(bookingService.confirmBook(String.valueOf(user.id()),"1",auth))
                .isEqualTo(Constants.USER_NOT_FOUND);
    }

    @Test
    void confirmBook_ExpectCannotFindBooking() {
        addTenMinutes();

        initBooking();

        String newStatus = bookingService.confirmBook(String.valueOf(user.id()), String.valueOf(0), auth);

        assertThat(newStatus).isEqualTo(Constants.CANNOT_FIND_BOOKING);
    }

    @Test
    void confirmBook_ExpectLoginUntoSameUser() {
        addTenMinutes();

        initBooking();

        User wrongUser = new User();
        wrongUser.login("wrong").pass("wrong").name("wrong").lastName("wrong").eMail("wrong@wrong.wrong").role(RolesEnum.USER.getRole());
        userRepo.save(wrongUser);

        String newStatus = bookingService.confirmBook(String.valueOf(wrongUser.id()), String.valueOf(booking.bookId()), auth);

        assertThat(newStatus).isEqualTo(Constants.LOGIN_UNTO_SAME_USER);

        userRepo.delete(wrongUser);
    }

    @Test
    void getUserRepo() {
        assertThat(bookingService.getUserRepo()).isNotNull();
    }

    @Test
    void getBookingRepo() {
        assertThat(bookingService.getBookingRepo()).isNotNull();
    }

    @Test
    void getScheduledService() {
        assertThat(bookingService.getScheduledService()).isNotNull();
    }

    @Test
    void getCalendarService() {
        assertThat(bookingService.getCalendarService()).isNotNull();
    }

    @Test
    void getNotification() {
        assertThat(bookingService.getNotification()).isNotNull();
    }

    @Test
    void getMillisToConfirm() {
        assertThat(bookingService.getMillisToConfirm()).isNotNull();
    }

    @Test
    void setMillisToConfirm() {
        bookingService.setMillisToConfirm(1000L);
        assertThat(bookingService.getMillisToConfirm()).isEqualTo(1000L);
    }


    void initAuth(String login){
        String token = jwtProvider.generateToken(login);
        jwtFilter.setAuthentication(token);
        auth = SecurityContextHolder.getContext().getAuthentication();
    }

    void addTenMinutes() {
        // add 10 minutes to sure book not in past
        bookingTime.setMinute(bookingTime.getMinute() + 10);
    }



    void putRoleDeletedToUser(){
        userRepo.delete(user);
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(user.login());
        auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}