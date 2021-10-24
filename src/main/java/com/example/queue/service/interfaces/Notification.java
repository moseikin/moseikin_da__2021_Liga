package com.example.queue.service.interfaces;

import com.example.queue.dto.UserDto;
import com.example.queue.entity.Booking;
import com.example.queue.entity.User;

public interface Notification {
    
    public void confirmation(Booking booking);

    void confirmed(Booking booking);

    void annulled(Booking booking);
}
