package ua.edu.ukma.cinemax.api.controller;

import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.cinemax.api.model.ApiFilm;
import ua.edu.ukma.cinemax.aspects.DelayMethod;
import ua.edu.ukma.cinemax.aspects.ParametersMethod;
import ua.edu.ukma.cinemax.exception.InvalidIDException;
import ua.edu.ukma.cinemax.model.Film;
import ua.edu.ukma.cinemax.service.FilmService;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/film")
public class FilmController {
    private static int requestId = 0;
    final static Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final String TMDB_API_KEY;
    private final FilmService filmService;

    public FilmController(@Value("${tmdb_api_key}") String key, @Autowired FilmService filmService) {
        this.TMDB_API_KEY = key;
        this.filmService = filmService;
    }

    @PostMapping("/add")
    public void add(@Valid @RequestBody ApiFilm film) {
        MDC.put("request_id", "film/add/:request_id: " + requestId++);
        filmService.add(film.toModel());
        MDC.clear();
    }

    @GetMapping(
            path = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @DelayMethod
    public List<ApiFilm> selectAll() {
        List<Film> films = filmService.getAll();
        List<ApiFilm> apiFilms = new ArrayList<>(films.size());
        films.forEach((f) -> apiFilms.add(new ApiFilm(f)));
        return apiFilms;
    }

    @GetMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ParametersMethod
    public ApiFilm select(@PathVariable Long id) {
        try {
            return new ApiFilm(filmService.get(id));
        } catch (EntityNotFoundException e) {
            throw new InvalidIDException("There's no such film with id = " + id, e);
        }
    }

    @GetMapping(path = "/details/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String selectDetails(@PathVariable Long id) {
        ApiFilm film = select(id);
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