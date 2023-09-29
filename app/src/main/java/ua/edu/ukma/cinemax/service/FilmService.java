package ua.edu.ukma.cinemax.service;

import ua.edu.ukma.cinemax.dto.FilmDto;

import java.util.List;

public interface FilmService {
    void add(FilmDto film);

    FilmDto get(Long id);

    List<FilmDto> getAll();

    void update(FilmDto film);

    void delete(Long id);
}
