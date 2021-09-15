package ru.digitalleague.singleton.notification;

import java.util.UUID;

public class OrderNotification implements Notification {
    private UUID id;

    private Float total;

    public OrderNotification(UUID id, Float total) {
        this.id = id;
        this.total = total;
    }

    public String getText() {
        return String.format("Заказ №%s оформлен. Общая сумма: %.2f руб.", id, total);
    }
}
