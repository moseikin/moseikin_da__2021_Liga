package ru.philosophyit.internship.javabase.time;

import java.util.*;

public class Main {
    private static final List<String> daysOfWeek = new ArrayList<>();
    private static final List<String> monthsList = new ArrayList<>();
    private static int daysAmountInMonth;
    private static int firstDayOfMonth;
    private static final Calendar calendar = Calendar.getInstance();

    public static void main(String ...args) throws InputMismatchException{
        // Отобразите календарь текущего месяца в консоли
        // например:
        // пн вт ср чт пт сб вс
        // 30 31 1  2  3  4  5
        // и т.д.
        CalendarItems.initDaysOfWeekList(daysOfWeek);
        CalendarItems.initMonthsList(monthsList);
        writeDateVariables();
    }


    private static void writeDateVariables(){

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int currentMonth = calendar.get(Calendar.MONTH);
        daysAmountInMonth = calendar.getActualMaximum(Calendar.DATE);
        firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println(monthsList.get(currentMonth) + " " + calendar.get(Calendar.YEAR));
        printCalendar();
    }

    private static void printCalendar() {
        for (int i = 0; i < daysOfWeek.size(); i++) {
            System.out.printf("%4s", daysOfWeek.get(i));
        }

        System.out.println("\n");

        int currentDay = firstDayOfMonth - 1;

        for (int i = 1; i <= daysAmountInMonth; i++) {
            if (currentDay == 0) currentDay = 7;
            else if (currentDay == 8) {
                currentDay = 1;
                System.out.println("\n");
            }

            if (i == 1 && currentDay != 1) {
                String daysStartsFrom = "%" + (4 * currentDay) + "s";
                System.out.printf(daysStartsFrom, i);
            } else {
                System.out.printf("%4s", i);
            }
            currentDay ++;
        }
        showChooseMonthQuestion();
    }

    private static void showChooseMonthQuestion() {
        System.out.print("\n Введите номер месяца: ");
        Scanner scanner = new Scanner(System.in);
        try {
            int numberInputted = scanner.nextInt();
            if (numberInputted < 1 || numberInputted > 12) throw new OutThisYearException();

            calendar.set(Calendar.MONTH, numberInputted - 1);
            System.out.println("\n\n\n");
            writeDateVariables();
        } catch (OutThisYearException | InputMismatchException e) {
            exceptionCaught();
        }
    }

    private static void exceptionCaught(){
        System.out.println("Необходимо ввести номер месяца от 1 до 12");
        showChooseMonthQuestion();
    }
}
