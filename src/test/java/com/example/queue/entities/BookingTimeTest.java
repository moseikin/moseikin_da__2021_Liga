package com.example.queue.entities;

import com.example.queue.TestEntities;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BookingTimeTest {

    @Test
    void testToString() {
        BookingTime bookingTime = new TestEntities().getBookingTime();
        String toString = "year=" + bookingTime.getYear() +
                        ", month=" + bookingTime.getMonth() +
                        ", day=" + bookingTime.getDay() +
                        ", hour=" + bookingTime.getHour() +
                        ", minute=" + bookingTime.getMinute();
        assertThat(bookingTime.toString()).isEqualTo(toString);

    }
}