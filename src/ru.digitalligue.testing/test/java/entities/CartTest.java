package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    private static final Map<String, Integer> cartMap = new HashMap<>();
    private static Cart cart;
    private static final User user = new User("qaz", "qwerty@gmail.com");


    @BeforeEach
    void init(){
        cart = new Cart();
        cart.addUser(user);
        cart.addToCart("Товар1", 3);
        cart.addToCart("Товар2", 4);

    }

    @Test
    void user_NotNull(){
        assertNotNull(user);
    }

    @Test
    void addToCart_Product_New_In_Cart() {
        cart.addToCart("Товар3", 2);
        int expected = 2;
        assertEquals(expected, cart.getCart().get("Товар3"));
    }

    @Test
    void addToCart_Product_Already_In_Cart() {
        int expected = cart.getCart().get("Товар1") + 2;
        cart.addToCart("Товар1", 2);
        assertEquals(expected, cart.getCart().get("Товар1"));
    }

    @Test
    void getCart() {
        cartMap.put("Товар1", 3);
        cartMap.put("Товар2", 4);
        assertEquals(cartMap, cart.getCart());
    }

    @Test
    void getUser() {
        assertEquals(cart.getUser().toString(), user.toString());
    }

    @Test
    void testToString() {
        cartMap.put("Товар1", 3);
        cartMap.put("Товар2", 4);
        assertEquals("Корзина: " + cartMap, cart.toString());
    }
}