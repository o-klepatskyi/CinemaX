package ua.edu.ukma.cinemax.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.converters.FilmConverter;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.repository.FilmRepository;
import ua.edu.ukma.cinemax.service.FilmService;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmConverter filmConverter;

    @Override
    public void add(FilmDto film) {
        filmRepository.save(filmConverter.createFrom(film));
    }

    @Override
    public Film get(Long id) {
        return filmRepository.getReferenceById(id);
    }

    @Override
    public List<FilmDto> getAll() {
        return filmConverter.createFromEntities(filmRepository.findAll());
    }

    @Override
    public void update(FilmDto film) {
        Film filmToUpdate = filmRepository.getReferenceById(film.getId());
        filmConverter.update(film, filmToUpdate);
        filmRepository.save(filmToUpdate);
    }

    @Override
    public void delete(Long id) {
        filmRepository.deleteById(id);
    }
}
