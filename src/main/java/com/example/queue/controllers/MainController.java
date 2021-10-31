package com.example.queue.controllers;

import com.example.queue.entities.BookingTime;
import com.example.queue.services.MainService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping("/")
public class MainController {

    private final MainService mainService;

    // поля: час и минута = 0 - смотрим заказы за весь день
    @GetMapping(path = "/bookings")
    public String getThisDayBookings(@Valid @RequestParam int year,
                                     @Valid @RequestParam int month,
                                     @Valid @RequestParam int day) {

        BookingTime bookingTime = new BookingTime();
        bookingTime.setYear(year);
        bookingTime.setMonth(month);
        bookingTime.setDay(day);
        bookingTime.setHour(0);
        bookingTime.setMinute(0);
        return mainService.getThisDayBookings(bookingTime);

    }

}
