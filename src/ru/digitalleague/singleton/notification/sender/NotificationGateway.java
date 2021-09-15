package ru.digitalleague.singleton.notification.sender;

import ru.digitalleague.singleton.notification.Notification;

public class NotificationGateway {
    private static NotificationGateway instance;

    private NotificationGateway() {

    }

    public void sendNotification(Notification notification) {
        System.out.println(notification.getText());
    }

    public static NotificationGateway getInstance() {
        if (instance == null) {
            instance = new NotificationGateway();
        }

        return instance;
    }
}
