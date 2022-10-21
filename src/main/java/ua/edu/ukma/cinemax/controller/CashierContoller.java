package ua.edu.ukma.cinemax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ukma.cinemax.repository.TicketRepository;

@RestController
public class CashierContoller {
    @Autowired
    private TicketRepository repository;

    @RequestMapping("/buy")
    public void buyTicket() {
        repository.update(1);
    }
}
