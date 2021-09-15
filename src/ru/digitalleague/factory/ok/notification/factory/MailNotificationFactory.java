package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.MailNotification;

public class MailNotificationFactory implements NotificationFactory {
    public Notification makeNotification(User user) {
        return new MailNotification(user);
    }
}
