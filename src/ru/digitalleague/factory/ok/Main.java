package ru.digitalleague.factory.ok;

import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;
import ru.digitalleague.factory.ok.notification.factory.MailNotificationFactory;
import ru.digitalleague.factory.ok.notification.factory.NotificationFactory;
import ru.digitalleague.factory.ok.notification.factory.PhoneNotificationFactory;
import ru.digitalleague.factory.ok.templates.HappyBirthDay;

import java.util.*;

public class Main {
    private static final HappyBirthDay[] TEMPLATE = HappyBirthDay.values();

    public static void main(String[] args) {

        printPossibleLocales();
        int chosenLang = chooseLanguage(TEMPLATE.length);

        User user = new User("Пользователь", "user@gmail.com", "+79522668105");

        SimpleNotification simpleNotification = new SimpleNotification(user, TEMPLATE);

        List<NotificationFactory> notificationFactories = new ArrayList<>();
        notificationFactories.add(new MailNotificationFactory());
        notificationFactories.add(new PhoneNotificationFactory());

        for(NotificationFactory factory : notificationFactories) {
            sendNotification(factory.makeNotification(simpleNotification, chosenLang));
        }
    }

    private static void printPossibleLocales() {
        for (int i = 0; i < TEMPLATE.length; i++) {
            System.out.print(i + " " + TEMPLATE[i].toString().toLowerCase(Locale.ROOT) + " ");
        }
        System.out.println("\n Выберите язык:  ");
    }

    private static int chooseLanguage(int languagesCount) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                int chosenLang = scanner.nextInt();
                if (chosenLang >= 0 & chosenLang < languagesCount) {
                    return chosenLang;
                } else wrongInput();
            } catch (InputMismatchException e) {
                wrongInput();
            }
        }
    }

    private static void wrongInput(){
        System.out.println("еррОр! еррОр! Такого номера нет");
        printPossibleLocales();
    }

    private static void sendNotification(Notification notification) {
            System.out.println(notification.getText());
    }

}
