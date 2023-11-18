package ua.edu.ukma.cinemax.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.service.FilmService;
import ua.edu.ukma.cinemax.service.ImageService;
import ua.edu.ukma.cinemax.service.SessionService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FilmController.class)
@ExtendWith(SpringExtension.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @MockBean
    private ImageService imageService;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser()
    @Test
    void selectAll_ShouldReturnAllFilms() throws Exception {
        List<FilmDto> films = new ArrayList<>();
        FilmDto filmDto = new FilmDto();
        filmDto.setId(1L);
        filmDto.setTmdbId(2L);
        films.add(filmDto);
        when(filmService.getAll()).thenReturn(films);
        when(imageService.getImageLink(anyLong())).thenReturn("imageLink");

        mockMvc.perform(get("/film/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("film/all"))
                .andExpect(model().attributeExists("films"))
                .andExpect(model().attribute("films", films))
                .andExpect(model().attribute("films", films));

        verify(filmService, times(1)).getAll();
        verify(imageService, times(films.size())).getImageLink(anyLong());
        verifyNoMoreInteractions(filmService, imageService);
    }

    @Test
    void getHtmlResponse_ShouldReturnHtml() throws Exception {
        mockMvc.perform(get("/film/all").accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));
    }
}
