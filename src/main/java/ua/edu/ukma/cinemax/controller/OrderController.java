package ua.edu.ukma.cinemax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.cinemax.dto.CinemaHallDto;
import ua.edu.ukma.cinemax.dto.Seat;
import ua.edu.ukma.cinemax.dto.converter.CinemaHallConverter;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.service.SessionService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final SessionService sessionService;
    private final CinemaHallConverter cinemaHallConverter;

    @GetMapping(path = "session/order/{id}")
    public String getOrderPage(@PathVariable("id") Long sessionId, Model model) {
        List<List<Seat>> seats = sessionService.getTicketStatus(sessionId);
        Session session = sessionService.get(sessionId);
        CinemaHallDto cinemaHallDto = cinemaHallConverter.createFrom(session.getCinemaHall());
        model.addAttribute("cinemaSession", session);
        model.addAttribute("cinemaHall", cinemaHallDto);
        model.addAttribute("seats", seats);
        return "session/order";
    }

    @PostMapping(path = "session/order/save/{id}")
    public String submitReservation(@PathVariable("id") Long session,
                                    @ModelAttribute("cinemaHall") CinemaHallDto cinemaHallDto,
                                    Principal principal) {
        sessionService.createTickets(session,
                cinemaHallDto.getSeats(),
                principal.getName());
        return "redirect:/session/order/" + session;
    }

}
