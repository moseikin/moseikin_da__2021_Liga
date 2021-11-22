package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;

public class SimpleNotification implements Notification {
    private final User user;
    private final Notification notification;
    private final String notificationChosen;


    public SimpleNotification(User user, String notificationChosen, Notification notification) {
        this.user = user;
        this.notificationChosen = notificationChosen;
        this.notification = notification;
    }

    @Override
    public String getText() {
        return String.format(notification.getText() +
                                "%s, " + notificationChosen, user.getName());
    }

    @Override
    public String getSource() {
        return "";
    }

}
