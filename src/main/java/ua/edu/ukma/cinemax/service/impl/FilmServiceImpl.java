package ua.edu.ukma.cinemax.service.impl;

import java.util.List;
import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.converters.FilmConverter;
import ua.edu.ukma.cinemax.exception.InvalidIDException;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.repository.FilmRepository;
import ua.edu.ukma.cinemax.service.FilmService;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    @Value("${tmdb_api_key}")
    private String TMDB_API_KEY;

    private final FilmRepository filmRepository;
    private final FilmConverter filmConverter;

    @Override
    public void add(FilmDto film) {
        filmRepository.save(filmConverter.createFrom(film));
    }

    @Override
    public Film get(Long id) {
        Optional<Film> maybeFilm = filmRepository.findById(id);
        if (maybeFilm.isEmpty())
            throw new InvalidIDException("No film with id " + id, null);
        return maybeFilm.get();
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

    @GetMapping(path = "/film/details/{id}")
    public JsonObject getDetails(@PathVariable Long id) {
        Film film = get(id);
        final String uri = String.format(
                "https://api.themoviedb.org/3/movie/%d?api_key=%s",
                film.getTmdbId(), TMDB_API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(uri, String.class);
            return JsonParser.parseString(result).getAsJsonObject();
        } catch (Exception ignored) {
            return null;
        }
    }
}
