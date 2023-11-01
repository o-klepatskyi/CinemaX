package ua.edu.ukma.cinemax.controller;

import com.netflix.discovery.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.service.FilmService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/film")
public class FilmController {
    private final FilmService filmService;
    private static final Logger LOGGER = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/greeting")
    @ResponseBody
    public String greeting() {
        return String.format(
                "Hello from '%s'!", eurekaClient.getApplication(appName).getName());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        LOGGER.info("Getting all films");
        return ResponseEntity.ok(filmService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable Long id) {
        return filmService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FilmDto> createFilm(@RequestBody FilmDto Film) {
        LOGGER.info("Creating film");
        return ResponseEntity.ok(filmService.add(Film));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmDto> updateFilm(@PathVariable Long id, @RequestBody FilmDto updatedFilm) {
        LOGGER.info("Updating film with id: " + id);
        updatedFilm.setId(id);
        return ResponseEntity.ok(filmService.update(updatedFilm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        LOGGER.info("Deleting film with id: " + id);
        filmService.delete(id);
        return ResponseEntity.ok().build();
    }

}
