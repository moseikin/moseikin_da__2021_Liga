package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private static User user;

    @BeforeAll
    static void setup() {
        user = new User("qaz", "qwerty@gmail.com");
    }

    @Test
    void testToString() {
        assertEquals(user.toString(), "Имя: qaz, email: qwerty@gmail.com");
    }

    // при ручном вводе параметров пользователя проверять на пустые поля:
    // if(user.getName != null && user.getName.isEmpty) {
    //            Assert.fail("Пустой пользователь")
    // }

}