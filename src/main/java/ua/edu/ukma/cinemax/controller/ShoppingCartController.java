package ua.edu.ukma.cinemax.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.edu.ukma.cinemax.dto.SessionDto;
import ua.edu.ukma.cinemax.dto.ShoppingCartDto;
import ua.edu.ukma.cinemax.dto.OrderDto;
import ua.edu.ukma.cinemax.dto.converter.OrderConverter;
import ua.edu.ukma.cinemax.dto.converter.SessionConverter;
import ua.edu.ukma.cinemax.dto.converter.ShoppingCartConverter;
import ua.edu.ukma.cinemax.persistance.entity.Order;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;
import ua.edu.ukma.cinemax.service.OrderService;
import ua.edu.ukma.cinemax.service.ShoppingCartService;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartConverter shoppingCartConvertor;
    private final OrderConverter orderConvertor;
    private final SessionConverter sessionConverter;

    @GetMapping("/order/user/{id}")
    ShoppingCartDto getCurrentOrder(@PathVariable Long id) {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(id);
        return shoppingCartConvertor.createFrom(shoppingCart);
    }

    @PostMapping("/order/complete/{id}")
    OrderDto completeOrder(@RequestBody final ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartConvertor.createFrom(shoppingCartDto);
        Order order = orderService.completeOrder(shoppingCart);
        return orderConvertor.createFrom(order);
    }

    @PostMapping("/order/new/{id}")
    void createNewOrder(@PathVariable Long id, @RequestBody final SessionDto sessionDto) {
        Session session = sessionConverter.createFrom(sessionDto);
        shoppingCartService.addSession(session, id);
    }
}
