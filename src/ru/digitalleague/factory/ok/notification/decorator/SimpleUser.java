package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.User;

public class SimpleUser implements DecoratorInterface {
    private final String notification;
    private final User user;

    public SimpleUser(String notification, User user) {
        this.notification = notification;
        this.user = user;
    }

    @Override
    public String getText() {
        return String.format(notification, user.getName());
    }

    public User getUser() {
        return user;
    }

    public String getNotification() {
        return notification;
    }
}
