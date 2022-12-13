package ua.edu.ukma.cinemax.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.persistance.entity.Order;
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;
import ua.edu.ukma.cinemax.persistance.entity.User;
import ua.edu.ukma.cinemax.persistance.repository.OrderRepository;
import ua.edu.ukma.cinemax.service.OrderService;
import ua.edu.ukma.cinemax.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ShoppingCartService shoppingCartService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setTickets(new ArrayList<>(shoppingCart.getTickets()));
        order.setUser(shoppingCart.getUser());
        order.setOrderTime(LocalDateTime.now());
        shoppingCartService.clearShoppingCart(shoppingCart);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersHistory(User user) {
        return orderRepository.getByUser(user);
    }
}
