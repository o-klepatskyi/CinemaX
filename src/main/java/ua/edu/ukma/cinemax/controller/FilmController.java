package ua.edu.ukma.cinemax.controller;

import ua.edu.ukma.cinemax.service.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/film")
public class FilmController {

    private final FilmService filmService;
}
