package ua.edu.ukma.cinemax.service.impl;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.edu.ukma.cinemax.exception.InvalidIDException;
import ua.edu.ukma.cinemax.model.Film;
import ua.edu.ukma.cinemax.repository.FilmRepository;
import ua.edu.ukma.cinemax.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService {
    private static final Logger logger = LoggerFactory.getLogger(FilmServiceImpl.class);
    private final FilmRepository filmRepository;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film add(Film film) {
        logger.info("Adding film: {}", film);
        Film f = filmRepository.save(film);
        logger.info(MarkerFactory.getMarker("DATABASE"),"Added film with id " + f.getId());
        return f;
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
