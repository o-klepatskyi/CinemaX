package ua.edu.ukma.cinemax.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.cinemax.dto.CinemaHallDto;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.service.CinemaHallService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CinemaHallController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CinemaHallController.class);
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
        if (result.hasErrors()) {
            return "cinema-hall/add";
        }
        try {
            cinemaHallService.add(cinemaHall);
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
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

    @GetMapping(path = "/cinema-hall/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        CinemaHall cinemaHall = cinemaHallService.get(id);
        model.addAttribute("cinemaHall", cinemaHall);
        return "cinema-hall/edit";
    }

    @PostMapping(path = "/cinema-hall/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("cinemaHall") CinemaHallDto cinemaHall,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cinema-hall/edit";
        }
        try {
            cinemaHallService.update(cinemaHall);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return String.format("redirect:/cinema-hall/edit/%s?error", id);
        }

        return String.format("redirect:/cinema-hall/edit/%s?success", id);
    }

    @GetMapping(path = "cinema-hall/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            cinemaHallService.delete(id);
        } catch (Exception e) {
            return "redirect:/cinema-hall/all?delete_error";
        }
        return "redirect:/cinema-hall/all?success";
    }

}

