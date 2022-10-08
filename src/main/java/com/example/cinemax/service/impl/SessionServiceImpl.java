package com.example.cinemax.service.impl;

import com.example.cinemax.repository.FilmRepository;
import com.example.cinemax.repository.SessionRepository;
import com.example.cinemax.service.FilmService;
import com.example.cinemax.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;
}
