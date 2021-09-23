package entities;

import java.util.*;

public class Goods {
    private final Random random = new Random();

    private final Map<String, Integer> catalog = new HashMap<>();

    public Goods() {
        makeCatalog();
    }

    public void makeCatalog(){
        catalog.put("Диван", random.nextInt( 9) + 1);
        catalog.put("Чемодан", random.nextInt( 9) + 1);
        catalog.put("Саквояж", random.nextInt( 9) + 1);
        catalog.put("Картина", random.nextInt( 9) + 1);
        catalog.put("Корзина", random.nextInt( 9) + 1);
        catalog.put("Картонка", random.nextInt( 9) + 1);
    }

    public Map<String, Integer> getCatalog() {
        return catalog;
    }
}
