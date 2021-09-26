import entities.interfaces.DoShopping;

public class Main {


    public static void main(String[] args) {
        DoShopping doShopping = new DoShoppingImpl();
        doShopping.createNewUser();
        doShopping.printCatalog();
        doShopping.addToCart();
        doShopping.doContinueCarting();
    }

}
