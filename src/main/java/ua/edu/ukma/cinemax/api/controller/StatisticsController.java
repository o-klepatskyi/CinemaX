package ua.edu.ukma.cinemax.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ukma.cinemax.stat.service.StatisticsService;

@RestController
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @RequestMapping("/stat")
    public String printStat() {
        return statisticsService.getStat();
    }

}
