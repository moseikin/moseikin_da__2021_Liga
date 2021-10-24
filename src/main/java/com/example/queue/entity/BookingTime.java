package com.example.queue.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
// с @Accessors(fluent=true) swagger не видит example
public class BookingTime {
    @NotEmpty
    private int year;

    // в календаре месяц - с нуля
    @NotEmpty
    private int month;

    @NotEmpty
    private int day;

    @NotEmpty
    private int hour;

    @NotEmpty
    private int minute;


}
