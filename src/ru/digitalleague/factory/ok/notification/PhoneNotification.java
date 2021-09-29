package ru.digitalleague.factory.ok.notification;


import ru.digitalleague.factory.ok.User;

public class PhoneNotification implements Notification {
    private final User user;


    public PhoneNotification(User user) {
        this.user = user;
    }

    public String getText() {
        return String.format("\n (Это на телефон: %s) \n", user.getPhone());
    }

    @Override
    public String getSource() {
        return "телефон";
    }
}
