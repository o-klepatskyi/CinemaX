package ua.edu.ukma.cinemax.service.impl;

import ua.edu.ukma.cinemax.repository.FilmRepository;
import ua.edu.ukma.cinemax.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FilmServiceImpl implements FilmService {

    private FilmRepository filmRepository;

    @Autowired
    void setFilmRepository(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }
}
