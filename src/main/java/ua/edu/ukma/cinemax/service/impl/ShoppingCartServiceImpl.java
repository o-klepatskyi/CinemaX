package ua.edu.ukma.cinemax.service.impl;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;
import ua.edu.ukma.cinemax.persistance.entity.User;
import ua.edu.ukma.cinemax.persistance.repository.ShoppingCartRepository;
import ua.edu.ukma.cinemax.persistance.repository.TicketRepository;
import ua.edu.ukma.cinemax.service.ShoppingCartService;
import ua.edu.ukma.cinemax.service.UserService;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;
    private TicketRepository ticketRepository;
    private final UserService userService;

    @Override
    public void addSession(Session session, Long userId) {
        User user = userService.get(userId);
        Ticket newTicket = new Ticket();
        newTicket.setUser(user);
        newTicket.setFilmSession(session);
        ShoppingCart shoppingCart = getByUserId(userId);
        shoppingCart.getTickets().add(ticketRepository.save(newTicket));
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long id) {
        User user = userService.get(id);
        return shoppingCartRepository.getByUser(user).orElse(registerNewShoppingCart(user));
    }

    @Override
    public ShoppingCart registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setTickets(new ArrayList<>());
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    @Override
    public void clearShoppingCart(ShoppingCart shoppingCart) {
        ShoppingCart shoppingCartForClear =
                shoppingCartRepository.getReferenceById(shoppingCart.getId());
        shoppingCartForClear.setTickets(new ArrayList<>());
        shoppingCartRepository.save(shoppingCartForClear);
    }
}
