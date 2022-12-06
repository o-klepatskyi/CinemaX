package ua.edu.ukma.cinemax;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.edu.ukma.cinemax.api.controller.FilmController;
import ua.edu.ukma.cinemax.api.model.ApiFilm;
import ua.edu.ukma.cinemax.model.Film;
import ua.edu.ukma.cinemax.repository.FilmRepository;
import ua.edu.ukma.cinemax.service.FilmService;
import ua.edu.ukma.cinemax.service.impl.FilmServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
public class WebSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithAnonymousUser
    public void anonymousAccessToAllFilmsReturns200() throws Exception {
        mockMvc.perform(get("/api/film/all")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void addFilmWithoutUserReturns401() throws Exception {
        mockMvc.perform(post("/api/film/add", new ApiFilm())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="admin", password="admin", roles={"ADMIN"})
    public void addFilmWithAdminRoleReturns200() throws Exception {
        mockMvc.perform(
                        post("/api/film/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(film())))
                .andExpect(status().isOk());
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
