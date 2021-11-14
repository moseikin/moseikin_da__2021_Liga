package com.example.queue.services;

import com.example.queue.entities.Booking;
import com.example.queue.services.interfaces.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;

@Service
@PropertySource(value = {"classpath:queue.properties"})
@RequiredArgsConstructor
public class EmailService implements Notification {
    @Value(value = "${millisToConfirm}")
    private Long millisToConfirm;

    @Override
    public void confirmation(Booking booking) {
        long timeMillis = System.currentTimeMillis();
        long confirmTill = timeMillis + millisToConfirm;
        Time creationTime = new Time(timeMillis);
        Time timeToConfirm = new Time(confirmTill);

        String confirmationLink = "http://localhost:8080/user/confirm-book?userId=" + booking.user().id() +
                "&bookId=" + booking.bookId();
        System.out.println(confirmationLink);

        String body = "Заказ создан в " + creationTime +
                "\nПодтвердите ваш заказ до " + timeToConfirm +
                ", иначе ваш заказ будет анулирован";
        System.out.println(body);

    }

    @Override
    public void confirmed(Booking booking) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MMMM.yy HH.mm");
        String date = simpleDateFormat.format(booking.bookingTime());
        System.out.println("Ваш заказ подтвержден. \n Дата и время заказа: " + date);
    }

    @Override
    public void annulled(Booking booking) {
        System.out.println("Ваш заказ анулирован");
    }


}
