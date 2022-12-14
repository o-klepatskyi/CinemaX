package ua.edu.ukma.cinemax.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import ua.edu.ukma.cinemax.api.model.ApiCinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.service.CinemaHallService;

@Controller
@RequiredArgsConstructor
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;

    @GetMapping("/cinema-hall/add")
    public String add(Model model) {
        ApiCinemaHall apiCinemaHall = new ApiCinemaHall();
        model.addAttribute("cinemaHall", apiCinemaHall);
        return "cinema-hall/add";
    }

    @PostMapping("/cinema-hall/add")
    public String submitNewCinemaHall(@Valid @RequestBody @ModelAttribute("cinema-hall") ApiCinemaHall cinemaHall,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid cinema hall data");
            return "cinema-hall/add";
        }
        cinemaHallService.add(cinemaHall.toModel());
        return "redirect:/cinema-hall/add?success";
    }

    @GetMapping("/cinema-hall/all")
    public String selectAll(Model model) {
        List<CinemaHall> cinemaHalls = cinemaHallService.getAll();
        List<ApiCinemaHall> apiCinemaHalls = new ArrayList<>(cinemaHalls.size());
        cinemaHalls.forEach((ch) -> apiCinemaHalls.add(new ApiCinemaHall(ch)));
        model.addAttribute("cinemaHalls", apiCinemaHalls);
        return "cinema-hall/all";
    }

    @GetMapping(path = "/cinema-hall/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        CinemaHall cinemaHall = cinemaHallService.get(id);
        model.addAttribute("cinemaHall", cinemaHall);
        return "cinema-hall/edit";
    }

    @PostMapping(path = "/cinema-hall/edit/{id}")
    public String edit(@Valid @ModelAttribute("cinemaHall") ApiCinemaHall cinemaHall,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cinema-hall/edit";
        }
//        model.addAttribute("cinemaHall", cinemaHall);
        cinemaHallService.update(cinemaHall.toModel());
        return String.format("redirect:/cinema-hall/edit/%s?success", cinemaHall.getId());
    }
    @DeleteMapping(path = "cinema-hall/delete/{id}")
    public String delete(@PathVariable Long id) {
        cinemaHallService.delete(id);
        return "cinema-hall/all";
    }
}

