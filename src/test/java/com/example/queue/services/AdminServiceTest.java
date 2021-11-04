package com.example.queue.services;

import com.example.queue.TestEntities;
import com.example.queue.entities.Booking;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.RolesEnum;
import com.example.queue.entities.enums.StatusesEnum;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
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
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AdminServiceTest {
    TestEntities testEntities = new TestEntities();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    User user = testEntities.testUser();
    Booking booking = testEntities.testBooking(timestamp, user);

    @Autowired AdminService adminService;
    @Autowired BookingRepo bookingRepo;
    @Autowired UserRepo userRepo;

    @BeforeEach
    void setUp(){
        userRepo.save(user);
        bookingRepo.save(booking);
    }

    @Test
    void addAdmin() {
        User user = adminService.addAdmin();
        assertThat(user.role()).isEqualTo(RolesEnum.ADMIN.getRole());
    }

    @Test
    void setAppeared() {
        booking.status(StatusesEnum.STATUS_APPEARED.getStatus());
        bookingRepo.save(booking);

        assertThat(bookingRepo.findByBookId(booking.bookId()).status())
                .isEqualTo(StatusesEnum.STATUS_APPEARED.getStatus());
    }

    @Test
    void setCompleted() {
        booking.status(StatusesEnum.STATUS_COMPLETED.getStatus());
        bookingRepo.save(booking);

        assertThat(bookingRepo.findByBookId(booking.bookId()).status())
                .isEqualTo(StatusesEnum.STATUS_COMPLETED.getStatus());
    }

    @Test
    void setAnnulled() {
        booking.status(StatusesEnum.STATUS_ANNULLED.getStatus());
        bookingRepo.save(booking);

        assertThat(bookingRepo.findByBookId(booking.bookId()).status())
                .isEqualTo(StatusesEnum.STATUS_ANNULLED.getStatus());
    }
}