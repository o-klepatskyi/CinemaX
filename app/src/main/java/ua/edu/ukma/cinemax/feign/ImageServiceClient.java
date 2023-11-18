package ua.edu.ukma.cinemax.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.edu.ukma.cinemax.dto.FilmImageUrlDto;

@FeignClient(name = "media-service", path = "/media")
public interface ImageServiceClient {

    @GetMapping("/film-image-url")
    FilmImageUrlDto getFilmImageURL(@RequestParam("id") Long id);

    @GetMapping(value = "/film-image", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getFilmImage(@RequestParam("id") Long id);
}
