import entities.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import java.nio.ByteBuffer;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DoShoppingImplTest  {

    private ByteArrayOutputStream testOut = new ByteArrayOutputStream();
    private final int test = 1;
    private byte[] bytes;
    private ByteArrayInputStream testIn;
    private static final User user = new User("newUser", "qwerty@gmail.com");
    private final DoShoppingImpl doShopping = new DoShoppingImpl();

//    @InjectMocks
//    private DoShoppingImpl doShopping;

    @BeforeEach
    void setUp(){
        System.setOut(new PrintStream(testOut));
        bytes = ByteBuffer.allocate(4).putInt(test).array();
        testIn = new ByteArrayInputStream(bytes);
        System.setIn(testIn);
    }

    @Test
    void testCreateNewUser(){
        doShopping.createNewUser();
    }

    @Test
    void testPrintCatalog(){
        doShopping.printCatalog();
    }

    @Test
    void testAddToCart(){
        assertThrows(NoSuchElementException.class, doShopping::addToCart);
    }

    @Test
    public void testDoContinueCarting(){
        assertThrows(NoSuchElementException.class, doShopping::doContinueCarting);
    }

    @Test
    void testGetUser(){
        doShopping.createNewUser();
        assertEquals(MainTest.removeSeparators(user.toString()),
                MainTest.removeSeparators(doShopping.getUser().toString()));
    }

}