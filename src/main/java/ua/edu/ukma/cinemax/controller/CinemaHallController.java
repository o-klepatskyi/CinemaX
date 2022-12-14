package ua.edu.ukma.cinemax.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import ua.edu.ukma.cinemax.validation.CinemaHallValidator;

@Controller
@RequiredArgsConstructor
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;
    private final CinemaHallValidator validator;

    @GetMapping("/cinema-hall/add")
    public String add(Model model) {
        CinemaHallDto cinemaHall = new CinemaHallDto();
        model.addAttribute("cinemaHall", cinemaHall);
        return "cinema-hall/add";
    }

    @PostMapping("/cinema-hall/add")
    public String submitNewCinemaHall(@Valid @ModelAttribute("cinemaHall") CinemaHallDto cinemaHall,
                                   BindingResult result, Model model) {
        validator.validateFieldConstraints(cinemaHall, result);
        if (result.hasErrors()) {
            return "cinema-hall/add";
        }
        cinemaHallService.add(cinemaHall);
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
    public String edit(@Valid @ModelAttribute("cinemaHall") CinemaHallDto cinemaHall,
                       BindingResult result, Model model) {
        validator.validateFieldConstraints(cinemaHall, result);
        if (result.hasErrors()) {
            return "cinema-hall/edit";
        }
        cinemaHallService.update(cinemaHall);
        return String.format("redirect:/cinema-hall/edit/%s?success", cinemaHall.getId());
    }
    @DeleteMapping(path = "cinema-hall/delete/{id}")
    public String delete(@PathVariable Long id) {
        cinemaHallService.delete(id);
        return "cinema-hall/all";
    }
}

