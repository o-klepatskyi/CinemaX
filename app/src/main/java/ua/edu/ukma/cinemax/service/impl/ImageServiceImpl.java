package ua.edu.ukma.cinemax.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.cinemax.dto.FilmImageUrlDto;
import ua.edu.ukma.cinemax.service.ImageService;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public byte[] getFilmImageById(Long tmdbId) {
        return restTemplate.getForObject(getImageLink(tmdbId), byte[].class);
    }

    @Override
    public String getImageLink(Long tmdbId) {
        ResponseEntity<FilmImageUrlDto> responseEntity = restTemplate.getForEntity(
                "http://localhost:15500/film-image-url?id=" + tmdbId,
                FilmImageUrlDto.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var body = responseEntity.getBody();
            if (body == null) return null;
            return body.getFilmImageUrl();
        } else {
            // Handle error or throw an exception if needed
            System.out.println("Error occurred: " + responseEntity.getStatusCode());
            return null;
        }
    }
}
