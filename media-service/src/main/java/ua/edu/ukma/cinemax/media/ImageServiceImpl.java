package ua.edu.ukma.cinemax.media;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Value("${tmdb_api_key}")
    private String tmdbApiKey;
    @Override
    public byte[] getFilmImageById(Long tmdbId) {
        return new RestTemplate().getForObject(getImageLink(tmdbId), byte[].class);
    }

    @Override
    public String getImageLink(Long tmdbId) {
        JsonObject filmDetails = getDetails(tmdbId);
        if (filmDetails == null) {
            return null; // todo default image???
        }
        String posterPath = filmDetails.get("poster_path").getAsString();
        return String.format("https://image.tmdb.org/t/p/w500%s", posterPath);
    }

    private JsonObject getDetails(Long tmdbId) {
        final String uri = String.format("https://api.themoviedb.org/3/movie/%d?api_key=%s", tmdbId, tmdbApiKey);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(uri, String.class);
            if (result == null) return null;
            return JsonParser.parseString(result).getAsJsonObject();
        } catch (Exception ignored) {
            return null;
        }
    }
}
