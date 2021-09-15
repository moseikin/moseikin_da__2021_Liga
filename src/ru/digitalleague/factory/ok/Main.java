package ru.digitalleague.factory.ok;

import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.factory.MailNotificationFactory;
import ru.digitalleague.factory.ok.notification.factory.NotificationFactory;
import ru.digitalleague.factory.ok.notification.factory.PhoneNotificationFactory;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        printPossibleLocales();
        Locale locale = chooseLocale();

        User user = new User(2L, "Пользователь", "user@gmail.com", "+79522668105", locale);
//        String source = "email";
        String source = "phone";
        NotificationFactory factory;
        if (source.equals("email")) factory = new MailNotificationFactory();
        else factory = new PhoneNotificationFactory();

        sendNotification(factory.makeNotification(user));
    }

    private static void printPossibleLocales() {
        System.out.println(" 0. Система \n 1. English(US) \n 2. Chinese");
        System.out.println("Выберите язык:  ");
    }

    private static Locale chooseLocale() {
        try {
            Locale locale;
            Scanner scanner = new Scanner(System.in);
            int scannerInt = scanner.nextInt();
            if (scannerInt == 1) locale = Locale.ENGLISH;
            else if (scannerInt == 2) locale = Locale.CHINA;
            else locale = Locale.getDefault();
            return locale;
        } catch (InputMismatchException e) {
            printPossibleLocales();
            return chooseLocale();
        }
    }

    private static void sendNotification(Notification notification) {
        System.out.println(notification.getText());
    }

}
