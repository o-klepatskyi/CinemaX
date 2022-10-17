package ua.edu.ukma.cinemax.service.impl;

import ua.edu.ukma.cinemax.repository.SessionRepository;
import ua.edu.ukma.cinemax.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;
}
