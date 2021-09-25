package entities;

import java.util.*;

public class Goods {
    private final Random random = new Random();
    private final Map<String, Integer> catalog = new HashMap<>();

    public Goods() {
        makeCatalog();
    }

    private void makeCatalog(){
        catalog.put("Диван", getRandomQuantity());
        catalog.put("Чемодан", getRandomQuantity());
        catalog.put("Саквояж", getRandomQuantity());
        catalog.put("Картина", getRandomQuantity());
        catalog.put("Корзина", getRandomQuantity());
        catalog.put("Картонка", getRandomQuantity());
    }

    private int getRandomQuantity(){
        return random.nextInt( 9) + 1; // 0 - 9
    }

    public Map<String, Integer> getCatalog() {
        return catalog;
    }
}
