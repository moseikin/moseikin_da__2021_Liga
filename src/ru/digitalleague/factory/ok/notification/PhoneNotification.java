package ru.digitalleague.factory.ok.notification;


import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;

public class PhoneNotification implements Notification {

    private final SimpleNotification simpleNotification;

    public PhoneNotification(SimpleNotification simpleNotification, int lang) {
        this.simpleNotification = simpleNotification;
        simpleNotification.setLang(lang);

    }

    public String getText() {
        return String.format("Это на телефон: %s \n" + simpleNotification.getText(), simpleNotification.getUser().getPhone());
    }
}
