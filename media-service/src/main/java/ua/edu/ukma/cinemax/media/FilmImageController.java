package ua.edu.ukma.cinemax.media;

import com.example.grpc.TestServiceProto;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.cinemax.grpc.TestClient;

import java.util.Iterator;

@Controller
@RequestMapping("/media")
public class FilmImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilmImageController.class);

    @Autowired
    private TestClient testClient;

    @Autowired
    private ImageService imageService;

    @GetMapping("/film-image-url")
    public ResponseEntity<FilmImageUrlDto> getFilmImageURL(@RequestParam("id") Long id) {
        LOGGER.info("Getting message with id: " + id);
        return ResponseEntity.ok(new FilmImageUrlDto(imageService.getImageLink(id)));
    }

    @GetMapping(
            value = "/film-image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getFilmImage(@RequestParam("id") Long id) {
        LOGGER.info("Getting message with id: " + id);
        return imageService.getFilmImageById(id);
    }

    @GetMapping("/grpc/data={data}/index={index}")
    public @ResponseBody String testMethod(@PathVariable("data") String data, @PathVariable("index") String index) {
        System.out.println("data: " + data + ", index: " + index);
        try {
            final TestServiceProto.TestResponse response = testClient.testMethod(data, index);
            return response.getResult();
        } catch (StatusRuntimeException exception) {
            final Status.Code code = exception.getStatus().getCode();
            return "Exception on level grpc with code: " + code + ", description: " + exception.getStatus().getDescription();
        }
    }

    @GetMapping("/grpc/stream/data={data}/index={index}")
    public @ResponseBody String testMethodStream(@PathVariable("data") String data, @PathVariable("index") String index) {
        System.out.println("data: " + data + ", index: " + index);
        try {
            StringBuilder result = new StringBuilder();
            final Iterator<TestServiceProto.TestResponse> response = testClient.testMethodStream(data, index);
            while (response.hasNext()) {
                result.append(response.next().getResult());
            }
            return result.toString();
        } catch (StatusRuntimeException exception) {
            final Status.Code code = exception.getStatus().getCode();
            return "Exception on level grpc with code: " + code + ", description: " + exception.getStatus().getDescription();
        }
    }
}