package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;

public interface NotificationFactory {
    Notification makeNotification(User user);
}
