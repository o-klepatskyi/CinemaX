package ua.edu.ukma.cinemax.media;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class FilmImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/film-image-url")
    public ResponseEntity<FilmImageUrlDto> getFilmImageURL(@RequestParam("id") Long id) {
        return ResponseEntity.ok(new FilmImageUrlDto(imageService.getImageLink(id)));
    }

    @GetMapping(
            value = "/film-image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getFilmImage(@RequestParam("id") Long id) {
        return imageService.getFilmImageById(id);
    }
}
