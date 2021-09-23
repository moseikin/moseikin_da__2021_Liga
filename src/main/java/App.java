import entities.Cart;
import entities.Goods;
import entities.OrderImpl;
import entities.User;
import entities.interfaces.Order;

import java.util.*;


public class App {
    private static final Goods goods = new Goods();
    private static final Cart CART = new Cart();
    private static final LinkedList<String> goodsList = new LinkedList<>();


    public static void main(String[] args) {
        newUser();
        printCatalog();
        addToCartMessage();
        doContinueCarting();
    }

    private static void newUser(){
        User user = new User("newUser", "qwerty@gmail.com");
        System.out.println(user);
        CART.addUser(user);
        System.out.println(CART);
    }

    private static void printCatalog() {
        System.out.println("Каталог");
        int number = 1;
        boolean goodsListIsEmpty = goodsList.isEmpty();
        for (Map.Entry<String, Integer> item : goods.getCatalog().entrySet()) {
            int inCart = 0;
            if (CART.getCart().get(item.getKey()) != null) inCart = CART.getCart().get(item.getKey());
            int available = item.getValue() - inCart;
            if (available > 0) {
                System.out.printf("%s: %s в наличии: %s \n", number, item.getKey(), available);
                number ++;
            } else {
                goodsList.remove(item.getKey());
            }
            if (goodsListIsEmpty) {
                goodsList.add(item.getKey());
            }
        }
    }

    private static void addToCartMessage() {
        String productChosen;
        Scanner scanner;
        while (true) {
            try {
                System.out.print("Введите порядковый номер товара: ");
                scanner = new Scanner(System.in);
                int productNumber = scanner.nextInt();
                if (productNumber > 0 & productNumber < goodsList.size() ) {
                    productChosen = goodsList.get(productNumber - 1);
                    System.out.printf("Выбрано: %s. Доступно для заказа: %s \n", productChosen, goods.getCatalog().get(productChosen));
                    break;
                } else {
                    System.out.println("Такого товара нет");
                }
            } catch (InputMismatchException e) {
                System.out.println("Такого товара нет");
            }

        }

        while (true) {
            try {
                System.out.print("Введите количество: ");
                scanner = new Scanner(System.in);
                int quantity = scanner.nextInt();
                if (quantity > 0 & quantity <= goods.getCatalog().get(productChosen)) {
                    System.out.println("Добавлено в корзину");
                    CART.addToCart(productChosen, quantity);
                    System.out.println(CART);
                    break;
                } else {
                    System.out.println("Такого количества нет в наличии");
                }
            } catch (InputMismatchException e) {
                System.out.println("Такого количества нет в наличии");
            }
        }
    }

    private static void doContinueCarting() {
        while (true) {
            System.out.println("\n 1. Продолжить покупки; \n 2. Оформить заказ");
            Scanner scanner = new Scanner(System.in);
            int choose = scanner.nextInt();
            if (choose == 1) {
                printCatalog();
                addToCartMessage();
//            doContinueCarting();
            } else if (choose == 2) {
                Order order = new OrderImpl();
                order.makeOrder(CART);
                break;
            }
        }

    }
}
