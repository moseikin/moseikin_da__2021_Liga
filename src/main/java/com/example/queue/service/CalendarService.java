package com.example.queue.service;


import com.example.queue.config.QueueParameters;
import com.example.queue.entity.BookingTime;
import com.example.queue.repo.BookingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarService {
    private final QueueParameters queueParameters;
    private final BookingRepo bookingRepo;

    // помещаем данные из объекта заказа в календарь
    public Calendar getCalendar(BookingTime bookingTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, bookingTime.getMonth());
        calendar.set(bookingTime.getYear(),
                calendar.get(Calendar.MONTH),
                bookingTime.getDay(),
                bookingTime.getHour(),
                bookingTime.getMinute(),
                0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public boolean checkDateNotInPast(BookingTime bookingTime) {
        long currentTime = System.currentTimeMillis();
        Calendar calendar = getCalendar(bookingTime);
        long orderTime = calendar.getTimeInMillis();
        return orderTime >= currentTime;
    }

    public boolean checkIsInScheduleBounds(BookingTime bookingTime) {
        Calendar calendar = getCalendar(bookingTime);
        long orderTime = calendar.getTimeInMillis();
        calendar = getWorkDayStartCalendar(calendar);


        long startWorkingTime = calendar.getTimeInMillis();
        if (orderTime < startWorkingTime) {
            return false;
        } else {
            long endWorkingTime = startWorkingTime + calcWorkDayInMillis();
            return orderTime <= endWorkingTime - queueParameters.timeForOrder();
        }
    }

    public Calendar getWorkDayStartCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, queueParameters.openHour());
        calendar.set(Calendar.MINUTE, queueParameters.openMinutes());
        return calendar;
    }

    public Timestamp bookingTimeToTimestamp(BookingTime bookingTime) {
        Calendar calendar = getCalendar(bookingTime);
        return new Timestamp(calendar.getTimeInMillis());
    }


    public boolean isThereSpaceInQueue(Timestamp timestamp, List<Timestamp> allBookings){
        // если заказ перед первым или после последнего, можно заказывать
        if ((allBookings.get(0).getTime() - timestamp.getTime()) >= queueParameters.timeForOrder() |
                ((timestamp.getTime() - allBookings.get(allBookings.size() - 1).getTime()) >= queueParameters.timeForOrder())) {
            return true;
        }

        // если заказ по времени в середине очереди
        int neighbourIndex1 = Integer.MAX_VALUE;
        int neighbourIndex2 = Integer.MAX_VALUE;
        long neighbourTime1 = Long.MAX_VALUE;
        long neighbourTime2 = Long.MAX_VALUE;

        // ищем двух ближайших соседей по времени и их индексы в очереди
        for (int i = 0; i < allBookings.size(); i ++) {

            // если элемент ближе по времени, чем время на заказ, то заказ сделать нельзя
            if (Math.abs(allBookings.get(i).getTime() - timestamp.getTime()) < queueParameters.timeForOrder()) {
                return false;
            }
            if (i != neighbourIndex2 &&
                    Math.abs(allBookings.get(i).getTime() - timestamp.getTime()) >= queueParameters.timeForOrder()) {
                neighbourTime1 = allBookings.get(i).getTime() - timestamp.getTime();
                neighbourIndex1 = i;
            }
            if (i != neighbourIndex1 &&
                    Math.abs(allBookings.get(i).getTime() - timestamp.getTime()) >= queueParameters.timeForOrder()) {
                neighbourTime2 = allBookings.get(i).getTime() - timestamp.getTime();
                neighbourIndex2 = i;
            }
        }
        return Math.abs(neighbourTime2 - neighbourTime1) >= queueParameters.timeForOrder() * 2;
    }

    // продолжитеьность рабочего дня в миллисекундах
    public Long calcWorkDayInMillis() {
        long hours = queueParameters.closingHour() - queueParameters.openHour();
        long minutes = queueParameters.closingMinute() - queueParameters.openMinutes();
        if (minutes < 0) {
            hours = hours -1;
            minutes = 60 + minutes;
        }
        return (hours * 3600000) + (minutes * 60000);
    }

    public List<Timestamp> getAllTimeStampsThisDay(BookingTime bookingTime){
        Calendar calendar = getCalendar(bookingTime);
        long startWorkDayMillis = getWorkDayStartCalendar(calendar).getTimeInMillis();
        Timestamp openingTimeStamp = new Timestamp(startWorkDayMillis);
        Timestamp closingTimestamp = new Timestamp(startWorkDayMillis + calcWorkDayInMillis());
        // получаем заказы между этими двемя таймштампами, отсортированные по времени
        return bookingRepo.findAllBookingTime(openingTimeStamp, closingTimestamp);
    }
}
