package com.example.queue.service.interfaces;

import com.example.queue.entity.Booking;

public interface Notification {
    
    void confirmation(Booking booking);

    void confirmed(Booking booking);

    void annulled(Booking booking);
}
