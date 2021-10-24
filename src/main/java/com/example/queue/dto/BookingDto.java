package com.example.queue.dto;

import com.example.queue.entity.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Setter
@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
public class BookingDto {
    private Timestamp bookingTime;
    private String name;
    private String lastName;
    private String email;

    public BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.name(booking.user().name())
        .lastName(booking.user().lastName())
        .bookingTime(booking.bookingTime())
        .email(booking.user().eMail());

        return bookingDto;
    }

    @Override
    public String toString() {
        return "\n bookingTime=" + bookingTime +
                ", name='" + name +
                ", lastName='" + lastName +
                ", email='" + email;
    }
}
