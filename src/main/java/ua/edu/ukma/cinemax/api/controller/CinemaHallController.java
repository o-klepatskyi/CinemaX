package ua.edu.ukma.cinemax.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ukma.cinemax.api.model.ApiCinemaHall;
import ua.edu.ukma.cinemax.persistance.model.CinemaHall;
import ua.edu.ukma.cinemax.service.CinemaHallService;

@RestController
@RequestMapping("/cinema_hall")
public class CinemaHallController {
    private static int requestId = 0;
    final static Logger logger = LoggerFactory.getLogger(CinemaHallController.class);

    private CinemaHallService cinemaHallService;

    @Autowired
    public CinemaHallController(CinemaHallService cinemaHallService) {
        this.cinemaHallService = cinemaHallService;
    }

    @PostMapping("/add")
    public void add(@RequestBody ApiCinemaHall cinemaHall) {
        MDC.put("request_id", "cinema_hall/add/:request_id: " + requestId++);
        cinemaHallService.add(cinemaHall.toModel());
        MDC.clear();
    }

    @GetMapping(
            path = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ApiCinemaHall> selectAll() {
        List<CinemaHall> cinemaHalls = cinemaHallService.getAll();
        List<ApiCinemaHall> apiCinemaHalls = new ArrayList<>(cinemaHalls.size());
        cinemaHalls.forEach(ch -> apiCinemaHalls.add(new ApiCinemaHall(ch)));
        return apiCinemaHalls;
    }

    @PutMapping(path = "edit/{id}")
    public void edit(@PathVariable Long id,
                     @RequestBody ApiCinemaHall cinemaHall) {
        cinemaHall.setId(id);
        cinemaHallService.update(cinemaHall.toModel());
    }

    @DeleteMapping(path = "delete/{id}")
    public void delete(@PathVariable Long id) {
        cinemaHallService.delete(id);
    }
}

