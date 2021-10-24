package com.example.queue.service;

import com.example.queue.Constants;
import com.example.queue.config.QueueParameters;
import com.example.queue.entity.BookingTime;
import com.example.queue.repo.BookingRepo;
import com.example.queue.component.BookingAbility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainService {

    private final BookingRepo bookingRepo;
    private final QueueParameters queueParameters;
    private final BookingAbility ability;

    public String getThisDayBookings(BookingTime bookingTime) {
        List<Timestamp> timestamps = bookingRepo.findAllBookingTime();
        List<Timestamp> interDayTimestamps = new ArrayList<>();
        if (timestamps.isEmpty()) {
            return Constants.QUEUE_IS_FREE;
        }

        bookingTime.setHour(queueParameters.openHour());
        bookingTime.setMinute(queueParameters.openMinutes());

        // перевод объекта в миллисекунды
        Timestamp openingTimeStamp = ability.bookingTimeToTimestamp(bookingTime);
        Timestamp closingTimeStamp = new Timestamp(openingTimeStamp.getTime() + calcWorkDayInMillis());

        timestamps.sort(Comparator.comparingLong(Timestamp::getTime));

        // выберем заказы за выбранный день в отдельный список
        for (Timestamp timestamp : timestamps) {
            if (timestamp.after(openingTimeStamp) && timestamp.before(closingTimeStamp)) {
                interDayTimestamps.add(timestamp);
            }
        }

        if (interDayTimestamps.isEmpty()) {
            return Constants.QUEUE_IS_FREE;
        }

        return String.valueOf(interDayTimestamps);
    }

    private Long calcWorkDayInMillis() {
        long hours = queueParameters.closingHour() - queueParameters.openHour();
        long minutes = queueParameters.closingMinute() - queueParameters.openMinutes();
        if (minutes < 0) {
            hours = hours -1;
            minutes = 60 + minutes;
        }
        return (hours * 3600000) + (minutes * 60000);
    }

}
