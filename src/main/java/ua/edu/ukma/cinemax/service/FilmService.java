package ua.edu.ukma.cinemax.service;

import java.util.List;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.persistance.entity.Film;

public interface FilmService {
    void add(FilmDto film);
    Film get(Long id);
    List<FilmDto> getAll();
    void update(FilmDto film);
    void delete(Long id);
    JsonObject getDetails(Long id);
}
