package ua.edu.ukma.cinemax.service;

import java.time.LocalDate;
import java.util.List;
import ua.edu.ukma.cinemax.persistance.model.Session;

public interface SessionService {
    Session add(Session session);

    Session get(Long id);
    List<Session> get();

    List<Session> getAvailableSessions(Long filmId, LocalDate date);

    void update(Session session);

    void delete(Long id);
}
