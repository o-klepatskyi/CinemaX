package ua.edu.ukma.cinemax.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ukma.cinemax.service.ImageService;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping(
            value = "/film/image/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getFilmImage(@PathVariable Long id) {
        return imageService.getFilmImageById(id);
    }

    @GetMapping(
            value = "/film/image/link/{id}"
    )
    public String getImageLink(@PathVariable Long id) {
        return imageService.getImageLink(id);
    }
}
