package com.example.queue.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
// с @Accessors(fluent=true) swagger не видит example
public class BookingTime {

    @Min(value = 2021)
    private int year;

    // в календаре месяц - с нуля
    @Min(value = 0)
    @Max(value = 11)
    private int month;

    @Min(value = 1)
    private int day;

    @Min(value = 0)
    @Max(value = 23)
    private int hour;

    @Min(value = 0)
    @Max(value = 59)
    private int minute;


}
