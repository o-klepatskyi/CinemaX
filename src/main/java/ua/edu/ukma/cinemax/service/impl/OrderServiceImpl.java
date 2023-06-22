package ua.edu.ukma.cinemax.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.persistance.entity.Order;
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;
import ua.edu.ukma.cinemax.persistance.entity.User;
import ua.edu.ukma.cinemax.persistance.repository.TicketRepository;
import ua.edu.ukma.cinemax.service.OrderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    //    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        List<Ticket> tickets = shoppingCart.getTickets();
        tickets.forEach(x -> x.setIsBought(true));
        ticketRepository.saveAll(tickets);
        return null;
    }

    @Override
    public List<Order> getOrdersHistory(User user) {
//        return orderRepository.getByUser(user);
        return null;
    }
}
