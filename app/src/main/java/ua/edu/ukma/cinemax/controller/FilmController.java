package ua.edu.ukma.cinemax.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.ukma.cinemax.commons.exception.InvalidIDException;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.SessionDto;
import ua.edu.ukma.cinemax.service.FilmService;
import ua.edu.ukma.cinemax.service.ImageService;
import ua.edu.ukma.cinemax.service.SessionService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FilmController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;
    private final ImageService imageService;
    private final SessionService sessionService;

    @GetMapping(value = "/film/all", produces = MediaType.TEXT_HTML_VALUE)
    public String selectAll(Model model) {
        List<FilmDto> films = filmService.getAll();
        films.forEach(x -> x.setImageLink(imageService.getImageLink(x.getTmdbId())));
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
            LOGGER.debug(e.getMessage());
            return "film/add?error"; // todo fix error
        }

        return "redirect:/film/add?success";
    }

    @GetMapping("/film/{id}")
    public ModelAndView select(@PathVariable Long id) {
        try {
            ModelAndView mav = new ModelAndView("film/details");
            FilmDto film = filmService.get(id);
            film.setImageLink(imageService.getImageLink(film.getTmdbId()));
            mav.addObject("film", film);
            List<SessionDto> sessions = sessionService.getAvailableSessions(id, LocalDate.now());
            mav.addObject("sessions", sessions);
            return mav;
        } catch (EntityNotFoundException e) {
            throw new InvalidIDException("There's no such film with id = " + id, e);
        }
    }

    @GetMapping(path = "/film/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        FilmDto film = filmService.get(id);
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
            LOGGER.error(e.getMessage());
            return String.format("redirect:/film/edit/%s?error", id);
        }

        return String.format("redirect:/film/edit/%s?success", id);
    }

    @GetMapping(path = "film/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            filmService.delete(id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "redirect:/film/all?delete_error";
        }
        return "redirect:/film/all?success";
    }

    @JmsListener(destination = "FilmsChangedTopic")
    public void receiveFilmEvent(String message) {
        LOGGER.info("Received film event: " + message);
    }

}
