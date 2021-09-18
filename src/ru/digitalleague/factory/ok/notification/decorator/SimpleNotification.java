package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;

public class SimpleUser implements Notification {
    private final User user;

    public SimpleUser(User user) {
        this.user = user;
    }

    @Override
    public String getText() {
        return "";
    }

}
