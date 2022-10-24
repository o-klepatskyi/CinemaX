package ua.edu.ukma.cinemax.stat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.repository.TicketStats;
import ua.edu.ukma.cinemax.stat.service.StatisticsService;

import java.util.Date;

@Service
@EnableScheduling
public class TicketStatServiceImpl implements StatisticsService {
    @Autowired
    private TicketStats ticketRepository;

    public void updateStatistics() {
        ticketRepository.updateData(); // !!! this needs to be database query
        System.out.println(new Date() + " | Updated statistics!");
    }

    public String getStat() {
        return new Date() + " | Total tickets bought: " + ticketRepository.totalTicketsBought();
    }
}
