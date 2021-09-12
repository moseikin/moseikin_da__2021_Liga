package ru.philosophyit.internship.javabase.files;

import java.io.File;
import java.util.Arrays;

public class Main {
    private static final StringBuilder stringBuilder = new StringBuilder();

    public static void main(String ...args) {

        // Отобразите рекурсивно дерево директорий src/main/java/ru/philosophyit/internship/javabase
        // например 
        // src/main/java/ru/philosophyit/internship/javabase/files/Main.java
        // src/main/java/ru/philosophyit/internship/javabase/locks/DeadLock.java
        // src/main/java/ru/philosophyit/internship/javabase/locks/LiveLock.java
        // src/main/java/ru/philosophyit/internship/javabase/threads/Completable.java
        // и т.д.
        /// Более удачные оформления вывода в консоль приветствуются

        try {
            File file = new File("src");
            scanPath(file);
        } catch (NullPointerException e) {
            System.out.println("Директория не найдена");
        }

    }

    private static void scanPath(File file){
        for (File file1 : file.listFiles()){
            if (!file1.getName().equals("resources")) {
                int pathLength = file1.getPath().length() - file1.getName().length();
                char[] emptyCharsArray = new char[pathLength];
                Arrays.fill(emptyCharsArray, ' ');
                stringBuilder.delete(0, stringBuilder.length() + 1);
                stringBuilder.append(emptyCharsArray).append("/").append(file1.getName());
                System.out.println(stringBuilder);

                if (file1.isDirectory()) scanPath(file1);
            }
        }
    }
}
