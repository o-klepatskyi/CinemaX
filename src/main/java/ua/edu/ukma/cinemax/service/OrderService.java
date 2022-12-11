package ua.edu.ukma.cinemax.service;

import java.util.List;
import ua.edu.ukma.cinemax.persistance.model.Order;
import ua.edu.ukma.cinemax.persistance.model.ShoppingCart;
import ua.edu.ukma.cinemax.persistance.model.User;

public interface OrderService {
    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getOrdersHistory(User user);
}
