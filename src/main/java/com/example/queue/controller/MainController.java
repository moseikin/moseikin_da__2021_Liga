package com.example.queue.controller;

import com.example.queue.entity.BookingTime;
import com.example.queue.service.MainService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@AllArgsConstructor
@Api(value = "Main CRUD operations")
@RequestMapping("/")
public class MainController {

    private final MainService mainService;

    // поля: час и минута = 0 - смотрим заказы за весь день
    @GetMapping(path = "/bookings")
    public String getThisDayBookings(@Valid @RequestParam Integer year,
                                     @Valid @RequestParam Integer month,
                                     @Valid @RequestParam Integer day) {
        BookingTime bookingTime = new BookingTime();
        bookingTime.setYear(year);
        bookingTime.setMonth(month);
        bookingTime.setDay(day);
        bookingTime.setHour(0);
        bookingTime.setMinute(0);
        return mainService.getThisDayBookings(bookingTime);

    }

}
