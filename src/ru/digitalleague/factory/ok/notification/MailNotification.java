package ru.digitalleague.factory.ok.notification;


import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;

public class MailNotification implements Notification {
    private final User user;

    public MailNotification(User user) {
        this.user = user;
    }

    public String getSource(){
        return "Email";
    }

    public String getText() {
        return String.format("\n (Это сообщение на почту: %s) \n", user.getEmail());
    }

}
