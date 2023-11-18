package ua.edu.ukma.cinemax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.service.FilmService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/film")
public class FilmController {
    private final FilmService filmService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FilmDto>> getAllFilms() {
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
        return ResponseEntity.ok(filmService.add(Film));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmDto> updateFilm(@PathVariable Long id, @RequestBody FilmDto updatedFilm) {
        updatedFilm.setId(id);
        return ResponseEntity.ok(filmService.update(updatedFilm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        filmService.delete(id);
        return ResponseEntity.ok().build();
    }

}
