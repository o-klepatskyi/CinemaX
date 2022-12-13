package ua.edu.ukma.cinemax.service.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.edu.ukma.cinemax.service.FilmService;
import ua.edu.ukma.cinemax.service.ImageService;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final FilmService filmService;

    @Override
    public byte[] getFilmImageById(Long id) {
        return new RestTemplate().getForObject(getImageLink(id), byte[].class);
    }

    @Override
    public String getImageLink(Long id) {
        JsonObject filmDetails = filmService.getDetails(id);
        String posterPath = filmDetails.get("poster_path").getAsString();
        return String.format(
                "https://image.tmdb.org/t/p/w500%s",
                posterPath);
    }
}
