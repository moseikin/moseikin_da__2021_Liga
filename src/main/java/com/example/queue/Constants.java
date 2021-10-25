package com.example.queue;

import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:queue.properties"})
public class Constants {

    public static final String EVERY_HOUR_TOP_WORKING_DAYS = "0 0 ${openHour}-${closingHour} * * MON-FRI";

    public static final String NOT_WORKING_TIME = "В это время не работаем";
    public static final String BOOKING_DONE = "Заказ сделан";
    public static final String THIS_DAY_GONE = "Этот день уже в прошлом";
    public static final String THIS_TIME_OCCUPIED = "На это время уже сделан заказ";
    public static final String QUEUE_IS_FREE = "Очередь пуста, записывайтесь на любое рабочее время";

    public static final String CANNOT_FIND_BOOKING = "Не удается найти заказ";
    public static final String REMOVING_SUCCEED = "Произведена отмена заказа";

    public static final String CONFIRMED = "Заявка подтверждена";
    public static final String APPEARED = "Пользователь явился";
    public static final String COMPLETED = "Заявка завершена";
    public static final String ANNULLED = "Заявка аннулирована";    // не подтвердил или не явился


    public static final String STATUS_APPEARED = "appeared";
    public static final String STATUS_ANNULLED = "annulled";
    public static final String STATUS_COMPLETED = "completed";
    public static final String STATUS_UNCONFIRMED = "unconfirmed";
    public static final String STATUS_CONFIRMED = "confirmed";

    public static final String CANNOT_FIND_NEAREST_ACTIVE = "Нет ближайших активных заявок";

    public static final String LOGIN_UNTO_SAME_USER = "Войдите в систему с теми данными, под которыми совершали заказ";
}
