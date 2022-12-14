package ua.edu.ukma.cinemax.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.cinemax.dto.CinemaHallDto;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.service.CinemaHallService;

@Controller
@RequiredArgsConstructor
public class CinemaHallController {
    private static final Logger logger = LoggerFactory.getLogger(CinemaHallController.class);
    private final CinemaHallService cinemaHallService;

    @GetMapping("/cinema-hall/add")
    public String add(Model model) {
        CinemaHallDto cinemaHall = new CinemaHallDto();
        model.addAttribute("cinemaHall", cinemaHall);
        return "cinema-hall/add";
    }

    @PostMapping("/cinema-hall/add")
    public String submitNewCinemaHall(@Valid @ModelAttribute("cinemaHall") CinemaHallDto cinemaHall,
                                   BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "cinema-hall/add";
        }
        try {
            cinemaHallService.add(cinemaHall);
        } catch(Exception ex) {
            logger.debug(ex.getMessage());
            return "redirect:/cinema-hall/add?error";
        }
        return "redirect:/cinema-hall/add?success";
    }

    @GetMapping("/cinema-hall/all")
    public String selectAll(Model model) {
        List<CinemaHallDto> cinemaHalls = cinemaHallService.getAll();
        model.addAttribute("cinemaHalls", cinemaHalls);
        return "cinema-hall/all";
    }

    @GetMapping(path = "cinema-hall/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            cinemaHallService.delete(id);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return "redirect:cinema-hall/all?error";
        }
        return "redirect:cinema-hall/all?success";
    }

    @GetMapping(path = "cinema-hall/view/{id}")
    public String getView(@PathVariable Long id, Model model) {
        CinemaHall cinemaHall = cinemaHallService.get(id);
        model.addAttribute("cinemaHall", cinemaHall);
        model.addAttribute("occupiedSeats", getOccupiedSeats(cinemaHall));
        return "cinema-hall/view";
    }

    // todo move this logic to ticket controller etc
    private boolean[][] getOccupiedSeats(CinemaHall c) {
        boolean[][] arr = new boolean[c.getAisles()][c.getSeatsPerAisle()];
        arr[0][0] = true;
        arr[1][1] = true;
        arr[2][2] = true;
        return arr;
    }
}

