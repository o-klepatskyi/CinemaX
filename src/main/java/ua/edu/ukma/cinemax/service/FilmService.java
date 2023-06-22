package ua.edu.ukma.cinemax.service;

import com.google.gson.JsonObject;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.persistance.entity.Film;

import java.util.List;

public interface FilmService {
    void add(FilmDto film);

    Film get(Long id);

    List<FilmDto> getAll();

    void update(FilmDto film);

    void delete(Long id);

    JsonObject getDetails(Long id);
}
