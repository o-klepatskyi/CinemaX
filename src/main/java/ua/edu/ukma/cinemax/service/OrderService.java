package ua.edu.ukma.cinemax.service;

import java.util.List;
import ua.edu.ukma.cinemax.model.Order;
import ua.edu.ukma.cinemax.model.ShoppingCart;
import ua.edu.ukma.cinemax.model.User;

public interface OrderService {
    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getOrdersHistory(User user);
}
