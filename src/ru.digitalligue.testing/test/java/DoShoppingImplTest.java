import entities.Cart;
import entities.Goods;
import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class DoShoppingImplTest  {
    private static Cart cart = new Cart();
    private DoShoppingImpl doShopping = new DoShoppingImpl();
    private static Goods goods = new Goods();
    private static int quantity = 2;
    private static User user = new User("qaz", "qwerty@gmail.com");
    private static String product = "Картина";
    private static String notProduct = "Автомобиль";
    private static List<String> goodsList = new ArrayList<>();

    @BeforeAll
    static void setUp(){
        goodsList.addAll(goods.getCatalog().keySet());
//        cart.addToCart(notProduct, quantity);
    }


    @Test
    void testCreateNewUser() {

        cart.addUser(user);
        assertEquals(user, cart.getUser());
    }

    @Test
    void testPrintCatalog_GoodList_NotEmpty(){


        assertFalse(goods.getCatalog().isEmpty());
    }



    @Test
    void testPrintCatalog_CartValue_Null(){
        assertNull(cart.getCart().get(notProduct));
    }

    @Test
    void testPrintCatalog_CartValue_NotNull(){
        cart.addToCart(product, quantity);
        assertNotNull(cart.getCart().get(product));
    }

    @Test
    void testPrintCatalog_Available_MoreThanZero(){

        int quantity = 0;
        cart.addToCart(product, quantity);
        int available = goods.getCatalog().get(product) - cart.getCart().get(product);
        assertNotEquals(0, available);
    }

    @Test
    void testAddToCart_ProductInBounds(){
        int productNumber = 3;
        assertTrue(productNumber < goodsList.size());
    }

    @Test
    void testAddToCart_Product_NotInBounds(){
        int productNumber = 10;
        assertFalse(productNumber < goodsList.size());
    }





//    @org.junit.Test(expected = InputMismatchException.class)
//    public void testAddToCart_ThrowsException(){
//        String data = "What_I_could_put_in_console";
//        InputStream stdin = System.in;
//        System.setIn(new ByteArrayInputStream(data.getBytes()));
//        Scanner scanner = new Scanner(System.in);
//        System.setIn(stdin);
//
////        String input = "any string";
////        System.setIn(new ByteArrayInputStream(input.getBytes()));
////        Scanner scanner = new Scanner(System.in);
//    }





    @Test
    void testDoContinueCarting() {
    }
}