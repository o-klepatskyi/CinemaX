package ua.edu.ukma.cinemax;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


import reactor.core.publisher.Mono;
import ua.edu.ukma.cinemax.api.model.ApiFilm;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void addFilm(){
        assert(webTestClient != null);
        webTestClient
                //.mutateWith(mockUser())
                .post()
                .uri("/api/film/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(film()), ApiFilm.class)
                .exchange()
                .expectStatus().isOk();
    }

    private static ApiFilm film() {
        return ApiFilm.builder()
                .id(1L)
                .title("Test film")
                .releaseYear(1999)
                .tmdbId(1L)
                .description("Test description")
                .build();
    }
}

