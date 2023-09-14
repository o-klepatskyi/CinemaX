package ua.edu.ukma.cinemax.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.service.FilmService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FILM_SERVICE_ENDPOINT = "http://localhost:15501/film/";

    @Override
    public void add(FilmDto film) {
        ResponseEntity<FilmDto> response = restTemplate.postForEntity(
                FILM_SERVICE_ENDPOINT,
                film,
                FilmDto.class
        );
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }
    }

    @Override
    public FilmDto get(Long id) {
        ResponseEntity<FilmDto> response = restTemplate.getForEntity(
                FILM_SERVICE_ENDPOINT + id,
                FilmDto.class
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new RuntimeException();
    }

    @Override
    public List<FilmDto> getAll() {
        ResponseEntity<FilmDto[]> response = restTemplate.getForEntity(
                FILM_SERVICE_ENDPOINT,
                FilmDto[].class
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            return List.of(response.getBody());
        }
        throw new RuntimeException();
    }

    @Override
    public void update(FilmDto film) {
        restTemplate.put(
                FILM_SERVICE_ENDPOINT + film.getId(),
                film,
                FilmDto.class
        );
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete(FILM_SERVICE_ENDPOINT + id);
    }
}
