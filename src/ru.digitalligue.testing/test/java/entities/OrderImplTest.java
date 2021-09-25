package entities;

import entities.interfaces.Order;
import messages.Messages;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class OrderImplTest {

    @Test
    void makeOrder() {
        Order order = new OrderImpl();
        User user = new User("qaz", "qwerty@gmail.com");
        Cart cart = new Cart();
        cart.addToCart("Товар1", 2);
        cart.addUser(user);
        order.makeOrder(cart);
        assertNotNull(cart);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        Messages.newOrder();
        Messages.printUserMessage(user.toString());
        Messages.printOrder("Товар1", 2);

        String newOrder = "\n Новый заказ:";
        String userString = user.toString();
        String orderString = "Товар1" + " " + 2;
        String expected = newOrder + "\n" + userString + "\n" + orderString;
        assertEquals(removeSeparators(expected), removeSeparators(outContent.toString()));

    }

    private String removeSeparators(String s) {
        return s.replaceAll("\n", "").replaceAll("\r", "");
    }

}