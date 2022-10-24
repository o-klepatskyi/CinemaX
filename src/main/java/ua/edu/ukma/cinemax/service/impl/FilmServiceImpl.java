package ua.edu.ukma.cinemax.service.impl;

import java.util.List;
import ua.edu.ukma.cinemax.model.Film;
import ua.edu.ukma.cinemax.repository.FilmRepository;
import ua.edu.ukma.cinemax.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film add(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film get(Long id) {
        return filmRepository.getReferenceById(id);
    }

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public void update(Film film) {
        Film filmToUpdate = filmRepository.getReferenceById(film.getId());
        filmToUpdate.setTitle(film.getTitle());
        filmToUpdate.setReleaseYear(film.getReleaseYear());
        filmToUpdate.setDescription(film.getDescription());
        filmRepository.save(filmToUpdate);
    }

    @Override
    public void delete(Long id) {
        filmRepository.deleteById(id);
    }
}
