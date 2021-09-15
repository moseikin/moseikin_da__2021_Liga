package ru.digitalleague.factory.ok.notification;


import ru.digitalleague.factory.ok.RandomSample;
import ru.digitalleague.factory.ok.Translated;
import ru.digitalleague.factory.ok.User;

public class PhoneNotification implements Notification {

    private final User user;

    public PhoneNotification(User user) {
        this.user = user;
    }

    public String getText() {
        String sample = new RandomSample().getRandomSample();
        return new Translated(sample, user).chooseNotification();
    }
}
