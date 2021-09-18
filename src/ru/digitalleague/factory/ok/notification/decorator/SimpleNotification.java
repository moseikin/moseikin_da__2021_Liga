package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.HappyBirthDayLanguages;
import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;

public class SimpleNotification implements Notification {
    private final User user;
    private final HappyBirthDayLanguages[] languages;
    private int lang;

    public SimpleNotification(User user) {
        this.user = user;
        languages = HappyBirthDayLanguages.values();
    }

    @Override
    public String getText() {
        return languages[lang].getDear() + user.getName() + ", " + languages[lang].getHappyBirthDay();
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public User getUser() {
        return user;
    }
}
