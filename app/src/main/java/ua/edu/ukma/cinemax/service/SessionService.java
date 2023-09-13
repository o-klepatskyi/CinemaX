package ua.edu.ukma.cinemax.service;

import ua.edu.ukma.cinemax.dto.Seat;
import ua.edu.ukma.cinemax.dto.SessionDto;
import ua.edu.ukma.cinemax.persistance.entity.Session;

import java.time.LocalDate;
import java.util.List;

public interface SessionService {
    void add(SessionDto session);

    Session get(Long id);

    List<SessionDto> get();

    List<SessionDto> getAvailableSessions(Long filmId, LocalDate date);

    void update(SessionDto session);

    void delete(Long id);

    List<List<Seat>> getTicketStatus(Long id);
}
