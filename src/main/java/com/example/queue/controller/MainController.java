package com.example.queue.controller;

import com.example.queue.entity.BookingTime;
import com.example.queue.entity.User;
import com.example.queue.service.AdminService;
import com.example.queue.service.MainService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Api(value = "Main CRUD operations")
@RequestMapping("/")
public class MainController {

    private final AdminService adminService;
    private final MainService mainService;

    // поля час и минута = 0 - смотрим заказы за весь день
    @GetMapping(path = "/free-time")
    public String getThisDayBookings(@RequestParam Integer year,
                                     @RequestParam Integer month,
                                     @RequestParam Integer day) {
        BookingTime bookingTime = new BookingTime();
        bookingTime.setYear(year);
        bookingTime.setMonth(month);
        bookingTime.setDay(day);
        bookingTime.setHour(0);
        bookingTime.setMinute(0);
        return mainService.getThisDayBookings(bookingTime);

    }

}
