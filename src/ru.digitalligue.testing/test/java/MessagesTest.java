import entities.Cart;
import entities.User;
import messages.Messages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MessagesTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final String product = "Товар";
    private int available = 4;
    private int count = 2;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void inputProductNumberMessage() {
        Messages.inputProductNumberMessage();
        assertEquals("Введите порядковый номер товара: ", outContent.toString());
    }

    @Test
    void inputQuantityMessage() {
        Messages.inputQuantityMessage();
        assertEquals("Введите количество: ", outContent.toString());
    }

    @Test
    void notEnoughMessage() {
        Messages.notEnoughMessage();
        assertEquals(removeSeparators("Такого количества нет в наличии"), removeSeparators(outContent.toString()));
    }

    @Test
    void addedToCartMessage() {
        Messages.addedToCartMessage();
        String expected = "Добавлено в корзину";
        assertEquals(removeSeparators(expected), removeSeparators(outContent.toString()));
    }

    @Test
    void showCartMessage() {
        Cart cart = new Cart();
        Messages.showCartMessage(cart);
        assertEquals(removeSeparators(cart.toString()), removeSeparators(outContent.toString()));
    }

    @Test
    void showChooseMessage() {
        Messages.showChooseMessage();
        String expected = "\n 1. Продолжить покупки; \n 2. Оформить заказ";
        assertEquals(removeSeparators(expected), removeSeparators(outContent.toString()));
    }

    @Test
    void noSuchProductMessage() {
        Messages.noSuchProductMessage();
        assertEquals(removeSeparators("Такого товара нет"), removeSeparators(outContent.toString()));
    }

    @Test
    void catalogMessage() {
        Messages.catalogMessage();
        assertEquals(removeSeparators("Каталог"), removeSeparators(outContent.toString()));
    }

    @Test
    void productLineMessage() {
        int number = 1;


        Messages.productLineMessage(number, product, available);
        String expected = String.format("%s: %s в наличии: %s \n", number, product, available);
        assertEquals(removeSeparators(expected), removeSeparators(outContent.toString()));
    }

    @Test
    void ableToBuyMessage() {

        Messages.ableToBuyMessage(product, count);
        String expected = String.format("Выбрано: %s. Доступно для заказа: %s \n", product, count);
        assertEquals(removeSeparators(expected), removeSeparators(outContent.toString()));
    }

    @Test
    void printUserMessage() {
        User user = new User("qaz", "qwerty@gmail.com");
        Messages.printUserMessage(user.toString());
        assertEquals(removeSeparators(user.toString()), removeSeparators(outContent.toString()));
    }

    @Test
    void newOrderTest(){
        Messages.newOrder();
        assertEquals(removeSeparators("\n Новый заказ:"), removeSeparators(outContent.toString()));
    }

    @Test
    void printOrderTest() {
        Messages.printOrder(product, count);
        assertEquals(removeSeparators(product + " " + count), removeSeparators(outContent.toString()));
    }

    @Test
    void wrongChoiceTest(){
        Messages.wrongChoice();
        assertEquals(removeSeparators("Ошибка ввода"), removeSeparators(outContent.toString()));
    }

    // удаляет line separators
    private String removeSeparators(String s) {
        return s.replaceAll("\n", "").replaceAll("\r", "");
    }
}