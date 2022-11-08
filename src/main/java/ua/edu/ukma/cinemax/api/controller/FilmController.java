package ua.edu.ukma.cinemax.api.controller;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping(path = "edit/{id}")
    public void edit(@PathVariable Long id,
                     @RequestBody ApiFilm film) {
        film.setId(id);
        filmService.update(film.toModel());
    }

    @DeleteMapping(path = "delete/{id}")
    public void delete(@PathVariable Long id) {
        filmService.delete(id);
    }

    @ExceptionHandler(InvalidFilmDataException.class)
    public void handleException() {

    }
}
