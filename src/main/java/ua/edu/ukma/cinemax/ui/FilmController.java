package ua.edu.ukma.cinemax.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.exception.InvalidIDException;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.service.FilmService;

@Controller
@RequiredArgsConstructor
public class FilmController {
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    @Value("${tmdb_api_key}")
    private String TMDB_API_KEY;
    private final FilmService filmService;

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
            model.addAttribute("film", film);
            return "film/add";
        }
        filmService.add(film);
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

    @GetMapping("/film/all")
    public ModelAndView selectAll() {
        ModelAndView mav = new ModelAndView("film/all");
        List<FilmDto> films = filmService.getAll();
        mav.addObject("films", films);
        return mav;
    }

    @GetMapping(path = "/film/details/{id}")
    public String selectDetails(@PathVariable Long id) {
        Film film = filmService.get(id);
        final String uri = String.format(
                "https://api.themoviedb.org/3/movie/%d?api_key=%s",
                film.getTmdbId(), TMDB_API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @PutMapping(path = "film/{id}")
    public void edit(@PathVariable Long id,
                     @RequestBody FilmDto film) {
        film.setId(id);
        try {
            filmService.update(film);
        } catch (EntityNotFoundException e) {
            throw new InvalidIDException("There's no such film with id = " + id, e);
        }
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        try {
            filmService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new InvalidIDException("There's no such film with id = " + id, e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            logger.info("Validation: " + fieldName + " " + errorMessage);
        });
        return errors;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(InvalidIDException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
