package ua.edu.ukma.cinemax.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.cinemax.feign.ImageServiceClient;
import ua.edu.ukma.cinemax.service.ImageService;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageServiceClient imageServiceClient;
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg";

    @Override
    public byte[] getFilmImageById(Long tmdbId) {
        try {
            return imageServiceClient.getFilmImage(tmdbId);
        } catch (Exception ignored) {
            return new RestTemplate().getForObject(DEFAULT_IMAGE, byte[].class);
        }
    }

    @Override
    public String getImageLink(Long tmdbId) {
        try {
            return imageServiceClient.getFilmImageURL(tmdbId).getFilmImageUrl();
        } catch (Exception ignored) {
            return DEFAULT_IMAGE;
        }
    }
}
