package ua.edu.ukma.cinemax.controller;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.exception.InvalidIDException;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.service.FilmService;
import ua.edu.ukma.cinemax.service.ImageService;

@Controller
@RequiredArgsConstructor
public class FilmController {
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;
    private final ImageService imageService;

    @GetMapping("/film/all")
    public String selectAll(Model model) {
        List<FilmDto> films = filmService.getAll();
        films.forEach(x -> x.setImageLink(imageService.getImageLink(x.getId())));
        model.addAttribute("films", films);
        return "film/all";
    }

    @GetMapping("/film/add")
    public String getAddFrom(Model model) {
        FilmDto film = new FilmDto();
        model.addAttribute("film", film);
        return "film/add";
    }

    @PostMapping("/film/add")
    public String submitNewFilm(@Valid @ModelAttribute("film") FilmDto film,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "film/add";
        }
        try {
            filmService.add(film);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return "film/add?error";
        }

        return "redirect:/film/add?success";
    }

    @GetMapping("/film/{id}")
    public ModelAndView select(@PathVariable Long id) {
        try {
            ModelAndView mav = new ModelAndView("film/details");
            mav.addObject("film", filmService.get(id));
            //mav.addObject("details", selectDetails(id));
            return mav;
        } catch (EntityNotFoundException e) {
            throw new InvalidIDException("There's no such film with id = " + id, e);
        }
    }

    @GetMapping(path = "/film/details/{id}")
    public String selectDetails(@PathVariable Long id) {
        return filmService.getDetails(id).getAsString();
    }

    @GetMapping(path = "/film/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        Film film = filmService.get(id);
        model.addAttribute("film", film);
        return "film/edit";
    }

    @PostMapping(path = "/film/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("film") FilmDto film,
                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "film/edit";
        }
        try {
            filmService.update(film);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return String.format("redirect:/film/edit/%s?error", id);
        }

        return String.format("redirect:/film/edit/%s?success", id);
    }

    @GetMapping(path = "film/delete/{id}") // todo how to delete method?
    public String delete(@PathVariable Long id) {
        filmService.delete(id);
        return "redirect:/film/all?success";
    }

}
