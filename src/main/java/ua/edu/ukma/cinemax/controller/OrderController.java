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
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.service.SessionService;

import java.util.List;

import static ua.edu.ukma.cinemax.persistance.entity.TicketStatus.STATUS_BOUGHT;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final SessionService sessionService;
    private final CinemaHallConverter cinemaHallConverter;
    private List<List<Seat>> seats;

    @GetMapping(path = "session/order/{id}")
    public String getOrderPage(@PathVariable("id") Long sessionId, Model model) {
        if (seats == null)
            seats = sessionService.getTicketStatus(sessionId);
        Session session = sessionService.get(sessionId);
        CinemaHallDto cinemaHallDto = cinemaHallConverter.createFrom(session.getCinemaHall());
        model.addAttribute("cinemaSession", session);
        model.addAttribute("cinemaHall", cinemaHallDto);
        model.addAttribute("seats", seats);
        return "session/order";
    }

    @PostMapping(path = "session/order/save/{id}")
    public String submitReservation(@PathVariable("id") Long session,
                                    @ModelAttribute("cinemaHall") CinemaHallDto cinemaHallDto) {
        System.out.println(cinemaHallDto.getSeats());
        CinemaHall cinemaHall = sessionService.get(session).getCinemaHall();
        for (Integer reserved : cinemaHallDto.getSeats()) {
            int x = reserved % cinemaHall.getSeatsPerAisle();
            int y = reserved / cinemaHall.getSeatsPerAisle();
            seats.get(y).get(x).setStatus(STATUS_BOUGHT);
        }
        return "redirect:/session/order/" + session;
    }

}
