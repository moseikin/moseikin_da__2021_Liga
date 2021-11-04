package com.example.queue;

import com.example.queue.entities.Booking;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.StatusesEnum;

import java.sql.Timestamp;

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
}
