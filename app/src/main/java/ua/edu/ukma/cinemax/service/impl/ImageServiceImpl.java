package ua.edu.ukma.cinemax.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.cinemax.dto.FilmImageUrlDto;
import ua.edu.ukma.cinemax.service.ImageService;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${media-service-url}")
    private String MEDIA_SERVICE_URL;

    @Override
    public byte[] getFilmImageById(Long tmdbId) {
        return restTemplate.getForObject(getImageLink(tmdbId), byte[].class);
    }

    @Override
    public String getImageLink(Long tmdbId) {
        try {
            ResponseEntity<FilmImageUrlDto> responseEntity = restTemplate.getForEntity(
                    MEDIA_SERVICE_URL + "?id=" + tmdbId,
                    FilmImageUrlDto.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                var body = responseEntity.getBody();
                if (body == null) return null;
                return body.getFilmImageUrl();
            } else {
                throw new Exception("Error occurred: " + responseEntity.getStatusCode());
            }
        } catch (Exception ignored) {
            return "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg";
        }
    }
}
