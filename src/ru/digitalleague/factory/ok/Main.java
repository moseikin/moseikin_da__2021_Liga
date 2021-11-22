package ru.digitalleague.factory.ok;

import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.decorator.SimpleNotification;
import ru.digitalleague.factory.ok.notification.factory.MailNotificationFactory;
import ru.digitalleague.factory.ok.notification.factory.NotificationFactory;
import ru.digitalleague.factory.ok.notification.factory.PhoneNotificationFactory;
import ru.digitalleague.factory.ok.templates.Templates;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static int chosenLang;
    private static final List<NotificationFactory> notificationFactories = new ArrayList<>();

    public static void main(String[] args) {
        User user = new User("Пользователь", "user@gmail.com", "+79522668105");
        notificationFactories.add(new MailNotificationFactory(user));
        notificationFactories.add(new PhoneNotificationFactory(user));

        printPossibleLocales();
        chooseLanguage();
        printPossibleNotifications();
        String notificationChosen = chooseNotification();
        printAvailableSources();
        NotificationFactory notificationFactory = notificationFactory();
        Notification notification = new SimpleNotification(user, notificationChosen, notificationFactory.makeNotification());

        sendNotification(notification);

    }

    private static void printPossibleLocales() {
        for (int i = 1; i <= Templates.LANGUAGES.getLocales().size(); i++) {
            System.out.print(i + " " + Templates.LANGUAGES.getLocales().get(i - 1) + " ");
        }
        System.out.print("\n Выберите язык:  ");
    }

    private static void chooseLanguage() {
        while (true) {
            try {
                chosenLang = scanner.nextInt() - 1;
                if (chosenLang >= 0 & chosenLang < Templates.LANGUAGES.getLocales().size()) {
                    return;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                wrongInput();
                printPossibleLocales();
                scanner.nextLine();
            }
        }
    }

    private static void printPossibleNotifications() {
        System.out.println("Доступны уведомления: ");
        for (int i = 1; i < Templates.values().length; i++) {
            System.out.println(i + ". " + Templates.values()[i]);
        }
    }

    private static String chooseNotification() {
        while (true) {
            try {
                System.out.print("\n Выберите уведомление:  ");
                int chosenNotification = scanner.nextInt();
                if (chosenNotification > 0 & chosenNotification < Templates.values().length) {
                    return Templates.values()[chosenNotification].getLocales().get(chosenLang);
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                wrongInput();
                printPossibleNotifications();
                scanner.nextLine();
            }
        }
    }

    private static void printAvailableSources(){
        for (int i = 1; i <= notificationFactories.size(); i++) {
            System.out.println(i + ". " + notificationFactories.get(i - 1).getSource());
        }
    }

    private static NotificationFactory notificationFactory(){
        while (true) {
            try{
                System.out.print("\n Куда направить уведомление: ");
                int chosenSource = scanner.nextInt() - 1;
                if (chosenSource >= 0 & chosenSource < notificationFactories.size()) {
                    return notificationFactories.get(chosenSource);
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                wrongInput();
                printAvailableSources();
                scanner.nextLine();
            }
        }
    }

    private static void wrongInput(){
        System.out.println("еррОр! еррОр! Такого номера нет");
    }


    private static void sendNotification(Notification notification) {
            System.out.println(notification.getText());
    }

}
