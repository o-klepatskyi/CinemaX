package ua.edu.ukma.cinemax.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.converter.FilmConverter;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.repository.FilmRepository;
import ua.edu.ukma.cinemax.service.FilmService;

import javax.jms.Topic;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmConverter filmConverter;
    private final JmsTemplate jmsTemplate;
    private final Topic filmTopic;

    @Override
    public FilmDto add(FilmDto film) {
        FilmDto addedFilm = filmConverter.createFrom(filmRepository.save(filmConverter.createFrom(film)));
        jmsTemplate.convertAndSend(filmTopic, "Film added: " + addedFilm.getId());
        return addedFilm;
    }

    @Override
    public Optional<FilmDto> get(Long id) {
        return filmRepository.findById(id).map(filmConverter::createFrom);
    }

    @Override
    public List<FilmDto> getAll() {
        return filmConverter.createFromEntities(filmRepository.findAll());
    }

    @Override
    public FilmDto update(FilmDto film) {
        Film filmToUpdate = filmRepository.getReferenceById(film.getId());
        filmConverter.update(film, filmToUpdate);
        FilmDto updatedFilm = filmConverter.createFrom(filmRepository.save(filmToUpdate));
        jmsTemplate.convertAndSend(filmTopic, "Film updated: " + updatedFilm.getId());
        return updatedFilm;
    }

    @Override
    public void delete(Long id) {
        filmRepository.deleteById(id);
        jmsTemplate.convertAndSend(filmTopic, "Film deleted: " + id);
    }
}
