package ua.edu.ukma.cinemax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.cinemax.dto.*;
import ua.edu.ukma.cinemax.dto.converter.CinemaHallConverter;
import ua.edu.ukma.cinemax.dto.converter.OrderConverter;
import ua.edu.ukma.cinemax.dto.converter.SessionConverter;
import ua.edu.ukma.cinemax.dto.converter.ShoppingCartConverter;
import ua.edu.ukma.cinemax.persistance.entity.Order;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;
import ua.edu.ukma.cinemax.service.OrderService;
import ua.edu.ukma.cinemax.service.SessionService;
import ua.edu.ukma.cinemax.service.ShoppingCartService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final SessionService sessionService;
    private final CinemaHallConverter cinemaHallConverter;
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartConverter shoppingCartConvertor;
    private final OrderConverter orderConvertor;
    private final SessionConverter sessionConverter;

    @GetMapping("/order/user/{username}")
    ShoppingCartDto getCurrentOrder(@PathVariable String username) {
        ShoppingCart shoppingCart = shoppingCartService.getByUsername(username);
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

    @GetMapping(path = "order/session/{id}")
    public String getOrderPage(@PathVariable("id") Long sessionId, Model model) {
        List<List<Seat>> seats = sessionService.getTicketStatus(sessionId);
        Session session = sessionService.get(sessionId);
        CinemaHallDto cinemaHallDto = cinemaHallConverter.createFrom(session.getCinemaHall());
        model.addAttribute("cinemaSession", session);
        model.addAttribute("cinemaHall", cinemaHallDto);
        model.addAttribute("seats", seats);
        return "order/session";
    }

    @PostMapping(path = "order/session/save/{id}")
    public String submitReservation(@PathVariable("id") Long session,
                                    @ModelAttribute("cinemaHall") CinemaHallDto cinemaHallDto,
                                    Principal principal) {
        shoppingCartService.addTickets(session,
                cinemaHallDto.getSeats(),
                principal.getName());
        return "redirect:/order/session/" + session + "?success";
    }

}
