package ru.digitalleague.singleton;

import ru.digitalleague.singleton.notification.RegisterNotification;
import ru.digitalleague.singleton.notification.sender.NotificationGateway;

public class Main {

    public static void main(String[] args) {
        NotificationGateway.getInstance().sendNotification(new RegisterNotification("Новый пользователь"));
    }
}
