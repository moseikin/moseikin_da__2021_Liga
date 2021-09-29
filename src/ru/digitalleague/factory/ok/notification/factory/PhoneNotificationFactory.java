package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.PhoneNotification;
import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;

public class PhoneNotificationFactory implements NotificationFactory {
    private final PhoneNotification phoneNotification;

    public PhoneNotificationFactory(User user) {
        phoneNotification = new PhoneNotification(user);
    }

    public Notification makeNotification() {
        return phoneNotification;
    }

    @Override
    public String getSource() {
        return phoneNotification.getSource();
    }
}
