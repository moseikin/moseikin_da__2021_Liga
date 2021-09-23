package entities;

import entities.interfaces.Order;

import java.util.Map;

public class OrderImpl implements Order {

    @Override
    public void makeOrder(Cart cart) {
        System.out.println("\n Новый заказ:");
        System.out.println(cart.getUser());
        for (Map.Entry<String, Integer> entry : cart.getCart().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

}
