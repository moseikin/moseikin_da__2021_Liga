package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.templates.HappyBirthDay;

public class SimpleNotification implements Notification {
    private final User user;
    private final HappyBirthDay[] template;

    private int lang;


    // По-хорошему, вместо HappyBirthDay[] на вход нужно принимать какой-то обобщенный Enum массив
    //  не соображу, что тут можно сделать
    public SimpleNotification(User user, HappyBirthDay[] template) {
        this.user = user;
        this.template = template;
    }


    @Override
    public String getText() {
        return template[lang].getDear() + user.getName() + ", " + template[lang].getHappyBirthDay();
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public User getUser() {
        return user;
    }
}
