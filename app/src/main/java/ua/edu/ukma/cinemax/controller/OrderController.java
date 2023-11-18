package ua.edu.ukma.cinemax.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.cinemax.dto.CinemaHallDto;
import ua.edu.ukma.cinemax.dto.Seat;
import ua.edu.ukma.cinemax.dto.converter.CinemaHallConverter;
import ua.edu.ukma.cinemax.dto.converter.SessionConverter;
import ua.edu.ukma.cinemax.dto.converter.ShoppingCartConverter;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final SessionService sessionService;
    private final CinemaHallConverter cinemaHallConverter;
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartConverter shoppingCartConvertor;
    private final SessionConverter sessionConverter;

    @GetMapping("/order/{username}")
    public String getCurrentOrder(@PathVariable String username, Model model) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(username);
        LOGGER.info(shoppingCart.toString());
        model.addAttribute("shoppingCart", shoppingCart);
        return "/order/shopping-cart";
    }

    @PostMapping("/order/{username}/complete")
    public String completeOrder(@PathVariable String username,
                                @ModelAttribute("shoppingCart") ShoppingCart shoppingCartDto) {
        orderService.completeOrder(shoppingCartDto);
        return "redirect:/order/" + username + "?success";
    }

    @GetMapping("/order/{username}/remove/{id}")
    public String deleteTicket(@PathVariable String username, @PathVariable Long id) {
        shoppingCartService.deleteTicket(id);
        return "redirect:/order/" + username + "?delete_success";
    }

//    @PostMapping("/new/{id}")
//    @Deprecated
//    void createNewOrder(@PathVariable Long id, @RequestBody final SessionDto sessionDto) {
//        Session session = sessionConverter.createFrom(sessionDto);
//        shoppingCartService.addSession(session, id);
//    }

    @GetMapping(path = "/order/session/{id}")
    public String getOrderPage(@PathVariable("id") Long sessionId, Model model) {
        List<List<Seat>> seats = sessionService.getTicketStatus(sessionId);
        Session session = sessionService.get(sessionId);
        CinemaHallDto cinemaHallDto = cinemaHallConverter.createFrom(session.getCinemaHall());
        model.addAttribute("cinemaSession", session);
        model.addAttribute("cinemaHall", cinemaHallDto);
        model.addAttribute("seats", seats);
        return "/order/session";
    }

    @PostMapping(path = "/order/session/save/{id}")
    public String submitReservation(@PathVariable("id") Long session,
                                    @ModelAttribute("cinemaHall") CinemaHallDto cinemaHallDto,
                                    Principal principal) {
        shoppingCartService.addTickets(session,
                cinemaHallDto.getSeats(),
                principal.getName());
        return "redirect:/order/session/" + session + "?success";
    }

}
