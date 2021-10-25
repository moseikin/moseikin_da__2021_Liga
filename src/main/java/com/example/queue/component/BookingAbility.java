package com.example.queue.component;


import com.example.queue.config.QueueParameters;
import com.example.queue.entity.BookingTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingAbility {
    private final QueueParameters queueParameters;

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

        // наверное, это проще контролировать фронтом
        return orderTime >= currentTime;
    }

    public boolean checkIsInScheduleBounds(BookingTime bookingTime) {

        Calendar calendar = getCalendar(bookingTime);
        long orderTime = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, queueParameters.openHour());
        calendar.set(Calendar.MINUTE, queueParameters.openMinutes());

        long startWorkingTime = calendar.getTimeInMillis();
        if (orderTime < startWorkingTime) {
            return false;
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, queueParameters.closingHour());
            calendar.set(Calendar.MINUTE, queueParameters.closingMinute());
            long endWorkingTime = calendar.getTimeInMillis();

            return orderTime <= endWorkingTime - queueParameters.timeForOrder();
        }
    }

    public Timestamp bookingTimeToTimestamp(BookingTime bookingTime) {
        Calendar calendar = getCalendar(bookingTime);
        return new Timestamp(calendar.getTimeInMillis());
    }


    public boolean isThereSpaceInQueue(Timestamp timestamp, List<Timestamp> allBookings){

        // если заказ перед первым или после последнего, можно заказывать
        if (timestamp.before(allBookings.get(0)) &&
                ((allBookings.get(0).getTime() - timestamp.getTime()) >= queueParameters.timeForOrder())){
            return true;
        }

        if (timestamp.after(allBookings.get(allBookings.size() - 1)) &&
                ((timestamp.getTime() - allBookings.get(allBookings.size() - 1).getTime()) >= queueParameters.timeForOrder())){
            return true;
        }
        // если заказ по времени в середине очереди
        // находим, кто перед нами по времени
        int neighbourIndex1 = Integer.MAX_VALUE;
        int neighbourIndex2 = Integer.MAX_VALUE;
        long neighbourTime1 = Long.MAX_VALUE;
        long neighbourTime2 = Long.MAX_VALUE;

        // ищем двух ближайших соседей по времени и их индексы в очереди
        for (int i = 0; i < allBookings.size(); i ++) {
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
}
