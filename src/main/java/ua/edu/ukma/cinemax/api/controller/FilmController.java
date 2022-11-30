package ua.edu.ukma.cinemax.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.ukma.cinemax.api.model.ApiFilm;
import ua.edu.ukma.cinemax.exception.InvalidFilmDataException;
import ua.edu.ukma.cinemax.exception.InvalidIDException;
import ua.edu.ukma.cinemax.model.Film;
import ua.edu.ukma.cinemax.service.FilmService;

@Controller
public class FilmController {
    private static int requestId = 0;
    final static Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final String TMDB_API_KEY;
    private final FilmService filmService;

    public FilmController(@Value("${tmdb_api_key}") String key, @Autowired FilmService filmService) {
        this.TMDB_API_KEY = key;
        this.filmService = filmService;
    }

//    @PostMapping("film/add")
//    public void add(@Valid @RequestBody ApiFilm film) {
//        MDC.put("request_id", "film/add/:request_id: " + requestId++);
//        filmService.add(film.toModel());
//        MDC.clear();
//    }

    @GetMapping("film/add")
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView("add-film");
        ApiFilm apiFilm = new ApiFilm();
        mav.addObject("film", apiFilm);
        return mav;
    }

    @PostMapping("film/add")
    public String submitNewFilm(@Valid @RequestBody @ModelAttribute("film") ApiFilm film,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid film data");
            return "redirect:/film/add";
        }
        filmService.add(film.toModel());
        return "redirect:/film/all";
    }

    @GetMapping("film/{id}")
    public ModelAndView select(@PathVariable Long id) {
        try {
            ModelAndView mav = new ModelAndView("selected-film");
            mav.addObject("film", new ApiFilm(filmService.get(id)));
            //mav.addObject("details", selectDetails(id));
            return mav;
        } catch (EntityNotFoundException e) {
            throw new InvalidIDException("There's no such film with id = " + id, e);
        }
    }

    @GetMapping("/film/all")
    public ModelAndView selectAll() {
        ModelAndView mav = new ModelAndView("list-films");
        List<Film> films = filmService.getAll();
        List<ApiFilm> apiFilms = new ArrayList<>(films.size());
        films.forEach((f) -> apiFilms.add(new ApiFilm(f)));
        mav.addObject("films", apiFilms);
        return mav;
    }

    @GetMapping(path = "film/details/{id}")
    public String selectDetails(@PathVariable Long id) {
        ApiFilm film = new ApiFilm(filmService.get(id));
        final String uri = String.format(
                "https://api.themoviedb.org/3/movie/%d?api_key=%s",
                film.getTmdbId(), TMDB_API_KEY);
        logger.info("Sending api request: " + uri);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @PutMapping(path = "/{id}")
    public void edit(@PathVariable Long id,
                     @RequestBody ApiFilm film) {
        film.setId(id);
        try {
            filmService.update(film.toModel());
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
