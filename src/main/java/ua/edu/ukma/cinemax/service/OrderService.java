package ua.edu.ukma.cinemax.service;

import java.util.List;
import ua.edu.ukma.cinemax.persistance.entity.Order;
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;
import ua.edu.ukma.cinemax.persistance.entity.User;

public interface OrderService {
    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getOrdersHistory(User user);
}
