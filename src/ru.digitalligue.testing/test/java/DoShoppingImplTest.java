import entities.Cart;
import entities.Goods;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoShoppingImplTest  {

    private Cart cart;
    private static final User user = new User("newUser", "qwerty@gmail.com");
    private DoShoppingImpl doShopping;


    @BeforeEach
    void setUp(){
        cart = new Cart();
        doShopping = new DoShoppingImpl(new Scanner(String.valueOf(1)));
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
    void testChooseProduct(){
        doShopping.printCatalog();
        assertThrows(NoSuchElementException.class, ()->doShopping.chooseProduct());
    }

    @Test
    void testChooseProduct_NoSuchProduct_NoSuchElementException(){
        doShopping = new DoShoppingImpl(new Scanner(String.valueOf(0)));
        doShopping.printCatalog();
        assertThrows(NoSuchElementException.class, ()->doShopping.chooseProduct());
    }


    @Test
    void testChooseProduct_NoSuchElementException(){
        assertThrows(NoSuchElementException.class, ()->doShopping.chooseProduct());
    }

    @Test
    void testChooseQuantity(){
        doShopping.printCatalog();
        Goods goods = new Goods();
        Map.Entry<String,Integer> entry = goods.getCatalog().entrySet().iterator().next();
        doShopping.chooseQuantity(entry.getKey());
    }

    @Test
    void testChooseQuantity_NotEnough_NoSuchElementException(){
        doShopping.printCatalog();
        Goods goods = new Goods();
        doShopping = new DoShoppingImpl(new Scanner(String.valueOf(0)));
        Map.Entry<String,Integer> entry = goods.getCatalog().entrySet().iterator().next();
        assertThrows(NoSuchElementException.class, ()->doShopping.chooseQuantity(entry.getKey()));
    }

    @Test
    void testChooseQuantity_NullPointerException(){
        String product = "someProduct";
        assertThrows(NullPointerException.class, ()->doShopping.chooseQuantity(product));
    }

    @Test
    void testChooseQuantity_InputMismatch_NoSuchElementException(){
        doShopping.printCatalog();
        Goods goods = new Goods();
        doShopping = new DoShoppingImpl(new Scanner("string"));
        Map.Entry<String,Integer> entry = goods.getCatalog().entrySet().iterator().next();
        assertThrows(NoSuchElementException.class, ()->doShopping.chooseQuantity(entry.getKey()));
    }

    @Test
    public void testDoContinueCarting_NoSuchElementException(){
        assertThrows(NoSuchElementException.class, ()->doShopping.doContinueCarting());
    }


    @Test
    public void testDoContinueCarting(){
        DoShoppingImpl doShopping1 = new DoShoppingImpl(new Scanner(String.valueOf(2)));
        doShopping1.createNewUser();
        cart.addToCart("product", 3);
        doShopping1.doContinueCarting();
    }

    @Test
    public void testDoContinueCarting_InputMismatch_NoSuchElementException(){
        DoShoppingImpl doShopping1 = new DoShoppingImpl(new Scanner("string"));
        doShopping1.createNewUser();
        cart.addToCart("product", 3);
        assertThrows(NoSuchElementException.class, doShopping1::doContinueCarting);
    }


    @Test
    void testGetUser(){
        doShopping.createNewUser();
        assertEquals(MainTest.removeSeparators(user.toString()),
                MainTest.removeSeparators(doShopping.getUser().toString()));
    }


}