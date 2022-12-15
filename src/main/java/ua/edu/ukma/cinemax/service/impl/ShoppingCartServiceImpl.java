package ua.edu.ukma.cinemax.service.impl;

import java.util.LinkedList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.persistance.entity.*;
import ua.edu.ukma.cinemax.persistance.repository.SessionRepository;
import ua.edu.ukma.cinemax.persistance.repository.TicketRepository;
import ua.edu.ukma.cinemax.service.ShoppingCartService;
import ua.edu.ukma.cinemax.service.UserService;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final SessionRepository sessionRepository;

    @Override
    public void addTickets(Long id, List<Integer> ticketsToRegister, String username) {
        Session session = sessionRepository.getReferenceById(id);
        User user = userService.getByUsername(username);
        if (session.getTickets() == null) {
            session.setTickets(new LinkedList<>());
        }
        CinemaHall cinemaHall = session.getCinemaHall();
        for (Integer reserved : ticketsToRegister) {
            int x = reserved % cinemaHall.getSeatsPerAisle();
            int y = reserved / cinemaHall.getSeatsPerAisle();
            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setIsBought(false);
            ticket.setFilmSession(session);
            ticket.setAisle(y);
            ticket.setSeat(x);
            session.getTickets().add(ticket);
        }
        sessionRepository.save(session);
    }

    @Override
    public ShoppingCart getShoppingCart(String username) {
        User user = userService.getByUsername(username);
        List<Ticket> tickets =  ticketRepository.getTicketsInShoppingCart(user.getId());
        return new ShoppingCart(null, tickets, user);
    }

    @Override
    public void clearShoppingCart(ShoppingCart shoppingCart) {
        ticketRepository.deleteAll(shoppingCart.getTickets());
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteTicket(id);
    }

//    @Override
//    @Deprecated
//    public ShoppingCart getByUserId(Long id) {
//        User user = userService.get(id);
//        return shoppingCartRepository.getByUser(user).orElse(registerNewShoppingCart(user));
//    }

//    @Override
//    @Deprecated
//    public ShoppingCart getByUsername(String username) {
//        User user = userService.getByUsername(username);
//        return shoppingCartRepository.getByUser(user).orElse(registerNewShoppingCart(user));
//    }

//    @Override
//    @Deprecated
//    public ShoppingCart registerNewShoppingCart(User user) {
//        ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.setUser(user);
//        shoppingCart.setTickets(new ArrayList<>());
//        shoppingCartRepository.save(shoppingCart);
//        return shoppingCart;
//    }

//    @Override
//    @Deprecated
//    public void addSession(Session session, Long userId) {
//        User user = userService.get(userId);
//        Ticket newTicket = new Ticket();
//        newTicket.setUser(user);
//        newTicket.setFilmSession(session);
//        ShoppingCart shoppingCart = getByUserId(userId);
//        shoppingCart.getTickets().add(ticketRepository.save(newTicket));
//        shoppingCartRepository.save(shoppingCart);
//    }
}
