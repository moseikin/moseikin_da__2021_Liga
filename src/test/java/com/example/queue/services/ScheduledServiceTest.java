package com.example.queue.services;

import com.example.queue.entities.Booking;
import com.example.queue.entities.enums.StatusesEnum;
import com.example.queue.repo.BookingRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/create-book-in-past.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ScheduledServiceTest {
    @Autowired ScheduledService scheduledService;
    @Autowired BookingRepo bookingRepo;

    @Test
    void annullingUnAppeared() {
        scheduledService.annullingUnAppeared();

        assertThat(bookingRepo.findByBookId(2147483648L).status())
                .isEqualTo(StatusesEnum.STATUS_ANNULLED.getStatus());
    }

    @Test
    void annullingUnAppeared_WithNullFields() {
        Booking booking = bookingRepo.findByBookId(2147483648L);
        booking.bookingTime(null).status(null);
        bookingRepo.save(booking);

        scheduledService.annullingUnAppeared();

        assertThat(bookingRepo.findByBookId(2147483648L).status())
                .isEqualTo(StatusesEnum.STATUS_ANNULLED.getStatus());
    }
}