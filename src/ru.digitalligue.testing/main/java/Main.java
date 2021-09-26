import entities.interfaces.DoShopping;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DoShopping doShopping = new DoShoppingImpl(new Scanner(System.in));
        doShopping.createNewUser();
        doShopping.printCatalog();
        doShopping.chooseProduct();
        doShopping.doContinueCarting();
    }

}
