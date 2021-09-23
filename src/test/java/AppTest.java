import entities.Cart;
import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    static User user;
    static Cart cart;

    @BeforeAll
    public static void setup(){
        user = new User("newUser", "12345@gmail.com");
        cart = new Cart();
    }

    @Test
    void newUser(){
        assertEquals(user.toString(), "Имя: newUser, email: 12345@gmail.com");

    }

    @Test
    void printCatalog(){
//        assertNotNull(App);
    }
}