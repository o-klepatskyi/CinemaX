package ua.edu.ukma.cinemax.service;

import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
//    void addSession(Session session, Long userId);

    void deleteTicket(Long id);

//    ShoppingCart getByUserId(Long id);
//    ShoppingCart getByUsername(String username);

//    ShoppingCart registerNewShoppingCart(User user);

    void clearShoppingCart(ShoppingCart shoppingCart);

    void addTickets(Long sessionId, List<Integer> ticketsToRegister, String username);

    ShoppingCart getShoppingCart(String username);
}
