package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.MailNotification;
import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;

public class MailNotificationFactory implements NotificationFactory {
    public Notification makeNotification(SimpleNotification simpleNotification, int lang) {
        return new MailNotification(simpleNotification, lang);
    }

}
