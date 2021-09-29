package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.MailNotification;
import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;

public class MailNotificationFactory implements NotificationFactory {
    MailNotification mailNotification;

    public MailNotificationFactory(User user) {
        mailNotification = new MailNotification(user);
    }

    public Notification makeNotification() {
        return mailNotification;
    }

    @Override
    public String getSource() {
        return "email";
    }


}
