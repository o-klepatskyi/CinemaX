package com.example.cinemax.controller;

import com.example.cinemax.service.FilmService;
import com.example.cinemax.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/film")
public class FilmController {

    private final FilmService filmService;
}
