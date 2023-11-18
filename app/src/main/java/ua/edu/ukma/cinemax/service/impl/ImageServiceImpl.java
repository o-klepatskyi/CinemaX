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

    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg";

    @Override
    public byte[] getFilmImageById(Long tmdbId) {
        try {
            return restTemplate.getForObject(MEDIA_SERVICE_URL + "/film-image?id=" + tmdbId, byte[].class);
        } catch(Exception ignored) {
            return restTemplate.getForObject(DEFAULT_IMAGE, byte[].class);
        }
    }

    @Override
    public String getImageLink(Long tmdbId) {
        try {
            return "/film/image/" + tmdbId;
        } catch (Exception ignored) {
            return DEFAULT_IMAGE;
        }
    }
}
