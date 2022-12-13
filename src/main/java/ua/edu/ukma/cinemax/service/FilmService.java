package ua.edu.ukma.cinemax.service;

import java.util.List;

import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.persistance.entity.Film;

public interface FilmService {
    void add(FilmDto film);
    Film get(Long id);
    List<FilmDto> getAll();
    void update(FilmDto film);
    void delete(Long id);
}
