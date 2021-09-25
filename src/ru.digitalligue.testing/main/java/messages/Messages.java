package messages;

import entities.Cart;

public class Messages {

    public static void inputProductNumberMessage() {
        System.out.print("Введите порядковый номер товара: ");
    }

    public static void inputQuantityMessage(){
        System.out.print("Введите количество: ");
    }

    public static void notEnoughMessage(){
        System.out.println("Такого количества нет в наличии");
    }

    public static void addedToCartMessage(){
        System.out.println("Добавлено в корзину");
    }

    public static void showCartMessage(Cart cart){
        System.out.println(cart);
    }

    public static void showChooseMessage(){
        System.out.println("\n 1. Продолжить покупки; \n 2. Оформить заказ");
    }



    public static void noSuchProductMessage() {
        System.out.println("Такого товара нет");
    }

    public static void catalogMessage() {
        System.out.println("Каталог");
    }

    public static void productLineMessage(int number, String name, int available) {
        System.out.printf("%s: %s в наличии: %s \n", number, name, available);

    }

    public static void ableToBuyMessage(String productChosen, Integer count) {
        System.out.printf("Выбрано: %s. Доступно для заказа: %s \n", productChosen, count);

    }

    public static void printUserMessage(String userString) {
        System.out.println(userString);
    }

    public static void newOrder() {
        System.out.println("\n Новый заказ:");
    }

    public static void printOrder(String key, Integer value) {
        System.out.println(key + " " + value);
    }

    public static void wrongChoice() {
        System.out.println("Ошибка ввода");
    }
}
