package entities;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private User user;
    private final Map<String, Integer> cartMap = new HashMap<>();

    public void addUser(User user) {
        this.user = user;
    }

    public void addToCart(String product, int quantity) {
        if (cartMap.containsKey(product)) {
            int inCartNow = cartMap.get(product);
            cartMap.put(product, inCartNow + quantity);
        } else {
            cartMap.put(product, quantity);
        }
    }

    public Map<String, Integer> getCart() {
        return cartMap;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return  "Корзина: " + cartMap;
    }
}
