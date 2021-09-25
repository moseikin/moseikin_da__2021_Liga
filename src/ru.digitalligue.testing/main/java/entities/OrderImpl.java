package entities;

import entities.interfaces.Order;
import messages.Messages;

import java.util.Map;

public class OrderImpl implements Order {

    @Override
    public void makeOrder(Cart cart) {
        Messages.newOrder();
        Messages.printUserMessage(cart.getUser().toString());

        for (Map.Entry<String, Integer> entry : cart.getCart().entrySet()) {
            Messages.printOrder(entry.getKey(), entry.getValue());
        }
    }

}
