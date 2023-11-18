package ua.edu.ukma.cinemax.service;

import ua.edu.ukma.cinemax.dto.FilmDto;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    FilmDto add(FilmDto film);

    Optional<FilmDto> get(Long id);

    List<FilmDto> getAll();

    FilmDto update(FilmDto film);

    void delete(Long id);
}
