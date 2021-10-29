package com.example.queue.service;

import com.example.queue.Constants;
import com.example.queue.entity.BookingTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainService {

    private final CalendarService ability;

    public String getThisDayBookings(BookingTime bookingTime) {
        List<Timestamp> interDayTimestamps = ability.getAllTimeStampsThisDay(bookingTime);

        // если список пуст, можно записываться на любое время
        if (interDayTimestamps.isEmpty()) {
            return Constants.QUEUE_IS_FREE;
        }

        return String.valueOf(interDayTimestamps);
    }



}
