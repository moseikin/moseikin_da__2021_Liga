package com.example.queue;

import com.example.queue.entities.Booking;
import com.example.queue.entities.BookingTime;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.StatusesEnum;

import java.sql.Timestamp;
import java.util.Calendar;

public class TestEntities {

    public User testUser(){
        return new User()
                .name("testUserName")
                .lastName("testUserLastName")
                .login("testUser")
                .pass("testUser")
                .eMail("testUser@testUser.ru")
                .role("ROLE_USER");
    }


    public Booking testBooking(Timestamp timestamp, User user){
        return new Booking()
                .bookingTime(timestamp)
                .user(user)
                .status(StatusesEnum.STATUS_UNCONFIRMED.getStatus());
    }

    public BookingTime getBookingTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BookingTime bookingTime = new BookingTime();
        bookingTime.setYear(calendar.get(Calendar.YEAR));
        bookingTime.setMonth(calendar.get(Calendar.MONTH));
        bookingTime.setDay(calendar.get(Calendar.DATE));
        bookingTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        bookingTime.setMinute(calendar.get(Calendar.MINUTE));
        return bookingTime;
    }
}
