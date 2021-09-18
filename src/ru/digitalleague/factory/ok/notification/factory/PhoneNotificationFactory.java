package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.PhoneNotification;
import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;

public class PhoneNotificationFactory implements NotificationFactory {
    public Notification makeNotification(SimpleNotification simpleNotification, int lang) {
        return new PhoneNotification(simpleNotification, lang);
    }
}
