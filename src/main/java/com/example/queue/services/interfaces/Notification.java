package com.example.queue.services.interfaces;

import com.example.queue.entities.Booking;

public interface Notification {
    
    void confirmation(Booking booking);

    void confirmed(Booking booking);

    void annulled(Booking booking);
}
