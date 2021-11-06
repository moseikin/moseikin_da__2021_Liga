package com.example.queue.repo;

import com.example.queue.TestEntities;
import com.example.queue.entities.Booking;
import com.example.queue.entities.User;
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
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookingRepoTest {
    TestEntities testEntities = new TestEntities();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 60000);
    User user = testEntities.testUser();
    Booking booking = testEntities.testBooking(timestamp, user);


    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    UserRepo userRepo;

    @BeforeEach
    void setUp(@Autowired BookingRepo bookingRepo, @Autowired UserRepo userRepo){
        userRepo.save(user);
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