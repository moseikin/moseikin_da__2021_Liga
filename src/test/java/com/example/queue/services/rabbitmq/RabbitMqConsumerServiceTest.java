package com.example.queue.services.rabbitmq;

import com.example.queue.TestEntities;
import com.example.queue.entities.Booking;
import com.example.queue.entities.BookingTime;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.StatusesEnum;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import com.example.queue.services.BookingService;
import com.example.queue.services.CalendarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = "classpath:before-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:after-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RabbitMqConsumerServiceTest {
    private final TestEntities testEntities = new TestEntities();
    private User user;
    private final Booking booking = new Booking();

    @Autowired CalendarService calendarService;
    @Autowired BookingRepo bookingRepo;
    @Autowired UserRepo userRepo;
    @Autowired RabbitMqConsumerService consumerService;

    @BeforeEach
    void setUp() {
        user = testEntities.testUser();
        userRepo.save(user);
        BookingTime bookingTime = testEntities.getBookingTime();
        booking.bookingTime(calendarService.bookingTimeToTimestamp(bookingTime));
        booking.status(StatusesEnum.STATUS_UNCONFIRMED.getStatus());
        booking.user(user);
        bookingRepo.save(booking);
    }

    @AfterEach
    void tearDown() {
        bookingRepo.delete(booking);
        userRepo.delete(user);
    }

    @Test
    void consumeTimeOutQueue() {
        consumerService.consumeTimeOutQueue(String.valueOf(booking.bookId()));
        assertThat(bookingRepo.findByBookId(booking.bookId()).status())
                .isEqualTo(StatusesEnum.STATUS_ANNULLED.getStatus());
    }
}