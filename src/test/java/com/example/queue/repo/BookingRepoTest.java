package com.example.queue.repo;

import com.example.queue.entities.Booking;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.StatusesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookingRepoTest {
    User user = new User();
    Booking booking = new Booking();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 60000);

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    UserRepo userRepo;

    @BeforeEach
    void setUp(@Autowired BookingRepo bookingRepo, @Autowired UserRepo userRepo){
        user
                .name("testUserName")
                .lastName("testUserLastName")
                .login("testUser")
                .pass("testUser")
                .eMail("testUser@testUser.ru")
                .role("ROLE_USER");
        userRepo.save(user);

        booking.bookingTime(timestamp)
                .user(user)
                .status(StatusesEnum.STATUS_UNCONFIRMED.getStatus());
        bookingRepo.save(booking);
    }

    @Test
    void findByBookId() {
        assertThat(bookingRepo.findByBookId(booking.bookId())).isNotNull();
    }

    @Test
    void findAllBookingTime() {
        assertThat(bookingRepo.findAllBookingTime(new Timestamp(System.currentTimeMillis()), timestamp))
                .isNotEmpty();
    }

    @Test
    void findByBookIdAndUserLogin() {
        assertThat(bookingRepo.findByBookIdAndUserLogin(booking.bookId(), user.login())).isNotNull();
    }

    @Test
    void findAllByStatusPageable() {
        Pageable pageable = PageRequest.of(0, 3);
        assertThat(bookingRepo.findAllByStatusPageable(pageable)).isNotEmpty();
    }

    @Test
    void findAllByStatus() {
        assertThat(bookingRepo.findAllByStatus()).isNotEmpty();
    }

    @Test
    void findAllByStatusAndUser() {
        assertThat(bookingRepo.findAllByStatusAndUser(user.id())).isNotEmpty();
    }
}