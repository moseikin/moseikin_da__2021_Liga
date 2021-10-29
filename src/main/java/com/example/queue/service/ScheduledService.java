package com.example.queue.service;

import com.example.queue.Constants;
import com.example.queue.entity.Booking;
import com.example.queue.entity.StatusesEnum;
import com.example.queue.repo.BookingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduledService {
    private final BookingRepo bookingRepo;

    // проходим каждый час в рабочее время рабочих дней
    // и анулируем неподтвержденные и подтвержденные заявки с прошедшей датой
    // с теми, где отмечено appeared и completed, не делаем ничего
    @Scheduled(cron = Constants.EVERY_HOUR_TOP_WORKING_DAYS)
    @Transactional
    public void annullingUnAppeared(){
        long millisNow = System.currentTimeMillis();
        Iterable<Booking> bookings = bookingRepo.findAll();
        for (Booking item : bookings) {
            if (item.bookingTime() != null & item.status() != null) {
                if (millisNow > item.bookingTime().getTime() &&
                        item.status().equals(StatusesEnum.STATUS_CONFIRMED.getStatus()) |
                                item.status().equals(StatusesEnum.STATUS_UNCONFIRMED.getStatus())){
                    setAnnulled(item);
                }
            } else {
                setAnnulled(item);
            }
        }
    }

    public void setAnnulled(Booking booking){
        booking.status(StatusesEnum.STATUS_ANNULLED.getStatus());
        bookingRepo.save(booking);
    }
}
