package ua.edu.ukma.cinemax.api.controller;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.cinemax.api.model.ApiFilm;
import ua.edu.ukma.cinemax.exception.InvalidFilmDataException;
import ua.edu.ukma.cinemax.exception.InvalidUserDataException;
import ua.edu.ukma.cinemax.model.Film;
import ua.edu.ukma.cinemax.service.FilmService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/film")
public class FilmController {
    private static int requestId = 0;
    final static Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final String TMDB_API_KEY = "f01d701a4e11f965d65f4fce27d098e5";
    private final FilmService filmService;

    @PostMapping("/add")
    public void add(@Validated @RequestBody ApiFilm film) {
        try {
            MDC.put("request_id", "film/add/:request_id: " + requestId++);
            filmService.add(film.toModel());
            MDC.clear();
        } catch (MethodArgumentNotValidException e) {
            throw new InvalidFilmDataException("Invalid film data", e);
        }
    }

    @GetMapping(
            path = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ApiFilm> selectAll() {
        List<Film> films = filmService.getAll();
        List<ApiFilm> apiFilms = new ArrayList<>(films.size());
        films.forEach((f) -> apiFilms.add(new ApiFilm(f)));
        return apiFilms;
    }

    @GetMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiFilm select(@PathVariable Long id) {
        return new ApiFilm(filmService.get(id));
    }

    @GetMapping(path = "/details/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String selectDetails(@PathVariable Long id) {
        Film film = filmService.get(id);
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
        filmService.update(film.toModel());
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        filmService.delete(id);
    }

    @ExceptionHandler(InvalidFilmDataException.class)
    public void handleException() {

    }
}
