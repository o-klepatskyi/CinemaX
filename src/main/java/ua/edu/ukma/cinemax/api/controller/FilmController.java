package ua.edu.ukma.cinemax.api.controller;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.cinemax.api.model.ApiFilm;
import ua.edu.ukma.cinemax.model.Film;
import ua.edu.ukma.cinemax.service.FilmService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/film")
public class FilmController {
    final static Logger logger = LoggerFactory.getLogger(FilmController.class);

    private final FilmService filmService;

    @PostMapping("/add")
    public void add(@RequestBody ApiFilm film) {
        filmService.add(film.toModel());
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
}
