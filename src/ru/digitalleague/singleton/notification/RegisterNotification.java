package ru.digitalleague.singleton.notification;

public class RegisterNotification implements Notification {
    private String login;

    public RegisterNotification(String login) {
        this.login = login;
    }

    public String getText() {
        return String.format("Пользователь '%s' зарегистрирован.", login);
    }
}
