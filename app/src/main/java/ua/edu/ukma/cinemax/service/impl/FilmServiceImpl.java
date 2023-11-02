package ua.edu.ukma.cinemax.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.feign.FilmServiceClient;
import ua.edu.ukma.cinemax.service.FilmService;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmServiceClient filmServiceClient;

    @Override
    public void add(FilmDto film) {
        filmServiceClient.addFilm(film);
    }

    @Override
    public FilmDto get(Long id) {
        return filmServiceClient.getFilm(id);
    }

    @Override
    public List<FilmDto> getAll() {
        return filmServiceClient.getAllFilms();
    }

    @Override
    public void update(FilmDto film) {
        filmServiceClient.updateFilm(film.getId(), film);
    }

    @Override
    public void delete(Long id) {
        filmServiceClient.deleteFilm(id);
    }
}
