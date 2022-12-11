package ua.edu.ukma.cinemax.service;

import java.util.List;
import ua.edu.ukma.cinemax.persistance.model.Film;

public interface FilmService {
    Film add(Film film);

    Film get(Long id);

    List<Film> getAll();

    void update(Film film);

    void delete(Long id);
}
