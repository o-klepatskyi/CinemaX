package ua.edu.ukma.cinemax.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.edu.ukma.cinemax.media.FilmImageController;
import ua.edu.ukma.cinemax.media.ImageService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmImageController.class)
public class FilmImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    void getFilmImage_ShouldReturnImage() throws Exception {
        long id = 1L;
        byte[] imageBytes = new byte[]{1, 2, 3};

        when(imageService.getFilmImageById(id)).thenReturn(imageBytes);

        mockMvc.perform(get("/film-image")
                        .param("id", String.valueOf(1L))
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_JPEG_VALUE));
    }
}