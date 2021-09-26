import entities.Cart;
import entities.Goods;
import entities.OrderImpl;
import entities.User;
import entities.interfaces.DoShopping;
import entities.interfaces.Order;
import messages.Messages;

import java.util.*;

public class DoShoppingImpl implements DoShopping {
    private final Scanner scanner;
    private static final Goods goods = new Goods();
    private static final Cart CART = new Cart();
    private User user;
    int inCart;

    public DoShoppingImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    public User getUser() {
        return user;
    }



    // костыль для того, чтобы связать порядковый номер товара из каталога в консоли с этим товаром
    private static final List<String> goodsList = new ArrayList<>();

    @Override
    public void createNewUser() {
        user = new User("newUser", "qwerty@gmail.com");
        Messages.printUserMessage(user.toString());
        CART.addUser(user);
        Messages.showCartMessage(CART);
    }

    @Override
    public void printCatalog() {
        Messages.catalogMessage();
        int number = 1;
        if (goodsList.isEmpty()) goodsList.addAll(goods.getCatalog().keySet());

        for (Map.Entry<String, Integer> item : goods.getCatalog().entrySet()) {
            int inCart = 0;
            if (CART.getCart().get(item.getKey()) != null) {
                inCart = CART.getCart().get(item.getKey());
            }
            int available = item.getValue() - inCart;
            if (available > 0) {
                Messages.productLineMessage(number, item.getKey(), available);
                number ++;
            } else {
                goodsList.remove(item.getKey());
            }
        }
    }

    @Override
    public void chooseProduct() {
        String productChosen;
        inCart = 0;
        while (true) {
            try {
                Messages.inputProductNumberMessage();
                int productNumber = scanner.nextInt();
                if (productNumber > 0 & productNumber <= goodsList.size()) {
                    productChosen = goodsList.get(productNumber - 1);
                    if (CART.getCart().get(productChosen) != null) {
                        inCart = CART.getCart().get(productChosen);
                    }
                    Messages.ableToBuyMessage(productChosen, goods.getCatalog().get(productChosen) - inCart);
                    break;
                } else {
                    Messages.noSuchProductMessage();
                }
            } catch (InputMismatchException e) {
                Messages.noSuchProductMessage();
                scanner.nextLine();
            }
        }

        chooseQuantity(productChosen);
    }

    @Override
    public void chooseQuantity(String productChosen) {
        while (true) {
            try {
                Messages.inputQuantityMessage();
                int quantity = scanner.nextInt();

                if (quantity > 0 & quantity <= goods.getCatalog().get(productChosen) - inCart) {
                    Messages.addedToCartMessage();
                    CART.addToCart(productChosen, quantity);
                    Messages.showCartMessage(CART);
                    break;
                } else {
                    Messages.notEnoughMessage();
                }
            } catch (InputMismatchException e) {
                Messages.notEnoughMessage();
                scanner.nextLine();
            }
        }
    }

    @Override
    public void doContinueCarting() {
        while (true) {
            try {
                Messages.showChooseMessage();
                int choose = scanner.nextInt();
                if (choose == 1) {
                    printCatalog();
                    chooseProduct();
                } else if (choose == 2) {
                    Order order = new OrderImpl();
                    order.makeOrder(CART);
                    break;
                }
            } catch (InputMismatchException e) {
                Messages.wrongChoice();
                scanner.nextLine();
            }

        }

    }





}
