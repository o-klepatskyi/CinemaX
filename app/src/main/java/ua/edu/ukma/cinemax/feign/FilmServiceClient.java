package ua.edu.ukma.cinemax.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.edu.ukma.cinemax.dto.FilmDto;

@FeignClient(name = "film-service", path = "/api/film")
public interface FilmServiceClient {

  @PostMapping("/")
  FilmDto addFilm(@RequestBody FilmDto film);

  @GetMapping("/{id}")
  FilmDto getFilm(@PathVariable("id") Long id);

  @GetMapping("/")
  List<FilmDto> getAllFilms();

  @PutMapping("/{id}")
  FilmDto updateFilm(@PathVariable("id") Long id, @RequestBody FilmDto film);

  @DeleteMapping("/{id}")
  void deleteFilm(@PathVariable("id") Long id);
}
