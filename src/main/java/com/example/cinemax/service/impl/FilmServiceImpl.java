package com.example.cinemax.service.impl;

import com.example.cinemax.repository.FilmRepository;
import com.example.cinemax.repository.UserRepository;
import com.example.cinemax.service.FilmService;
import com.example.cinemax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FilmServiceImpl implements FilmService {

    private FilmRepository filmRepository;

    @Autowired
    void setFilmRepository(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }
}
