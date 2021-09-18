package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;

public interface NotificationFactory {
    Notification makeNotification(SimpleNotification simpleNotification, int lang);
}
