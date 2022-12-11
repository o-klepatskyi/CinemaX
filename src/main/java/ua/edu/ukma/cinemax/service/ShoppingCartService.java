package ua.edu.ukma.cinemax.service;

import ua.edu.ukma.cinemax.persistance.model.Session;
import ua.edu.ukma.cinemax.persistance.model.ShoppingCart;
import ua.edu.ukma.cinemax.persistance.model.User;

public interface ShoppingCartService {
    void addSession(Session session, User user);

    ShoppingCart getByUser(User user);

    void registerNewShoppingCart(User user);

    void clearShoppingCart(ShoppingCart shoppingCart);
}
