package ua.edu.ukma.cinemax.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ukma.cinemax.repository.TicketStats;

@RestController
public class CashierContoller {
    @Autowired
    private TicketStats repository;

    @RequestMapping("/buy")
    public void buyTicket() {
        repository.update(1);
    }
}
