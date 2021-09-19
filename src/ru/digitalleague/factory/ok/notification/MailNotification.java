package ru.digitalleague.factory.ok.notification;


import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;

public class MailNotification implements Notification {

    private final SimpleNotification simpleNotification;

    public MailNotification(SimpleNotification simpleNotification, int lang) {
        this.simpleNotification = simpleNotification;
        this.simpleNotification.setLang(lang);
    }

    public String getText() {

        return String.format("\n (Это сообщение на почту: %s) \n" + simpleNotification.getText(), simpleNotification.getUser().getEmail()) ;


    }

}
