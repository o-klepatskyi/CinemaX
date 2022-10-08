package com.example.cinemax.controller;

import com.example.cinemax.service.FilmService;
import com.example.cinemax.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/session")
public class SessionController {
    private final SessionService sessionService;
}
