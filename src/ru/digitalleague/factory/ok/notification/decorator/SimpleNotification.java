package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.factory.NotificationFactory;

public class SimpleNotification implements Notification {
    private final User user;
    private final NotificationFactory notificationFactory;
    private final String notificationChosen;


    public SimpleNotification(User user, String notificationChosen, NotificationFactory notificationFactory) {
        this.user = user;
        this.notificationChosen = notificationChosen;
        this.notificationFactory = notificationFactory;
    }

    @Override
    public String getText() {
        return String.format(notificationFactory.makeNotification().getText() +
                                "%s, " + notificationChosen, user.getName());
    }

    @Override
    public String getSource() {
        return "";
    }

}
