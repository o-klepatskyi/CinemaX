package ua.edu.ukma.cinemax.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.dto.Seat;
import ua.edu.ukma.cinemax.dto.SessionDto;
import ua.edu.ukma.cinemax.dto.converter.CinemaHallConverter;
import ua.edu.ukma.cinemax.dto.converter.SessionConverter;
import ua.edu.ukma.cinemax.exception.InvalidIDException;
import ua.edu.ukma.cinemax.exception.InvalidSessionTime;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;
import ua.edu.ukma.cinemax.persistance.repository.SessionRepository;
import ua.edu.ukma.cinemax.service.SessionService;
import ua.edu.ukma.cinemax.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ua.edu.ukma.cinemax.dto.TicketStatus.STATUS_FREE;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private static final long SESSION_DURATION_HOURS = 2;
    //private static final LocalTime END_OF_DAY = LocalTime.of(23, 59, 59);
    private final SessionRepository sessionRepository;
    private final SessionConverter converter;
    private final CinemaHallConverter cinemaHallConverter;
    private final UserService userService;

    @Override
    public void add(SessionDto sessionDto) {
        checkAvailableTime(sessionDto);
        sessionRepository.save(converter.createFrom(sessionDto));
    }

    @Override
    public Session get(Long id) {
        Optional<Session> maybeSession = sessionRepository.findById(id);
        if (maybeSession.isEmpty()) {
            throw new InvalidIDException("No session with id " + id, null);
        }
        return maybeSession.get();
    }

    @Override
    public List<SessionDto> get() {
        return converter.createFromEntities(sessionRepository.findAll());
    }

    @Override
    public List<SessionDto> getAvailableSessions(Long filmId, LocalDate date) {
        return converter.createFromEntities(sessionRepository.getAvailableSessions(filmId, date));
    }

    @Override
    public void update(SessionDto session) {
        checkAvailableTime(session);
        Session sessionForUpdate = sessionRepository.getReferenceById(session.getId());
        converter.update(session, sessionForUpdate);
        sessionRepository.save(sessionForUpdate);
    }

    @Override
    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }

    @Override
    public List<List<Seat>> getTicketStatus(Long id) {
        Session session = get(id);
        CinemaHall hall = session.getCinemaHall();
        List<List<Seat>> seats = new ArrayList<>();
        for (int i = 0; i < hall.getAisles(); i++) {
            List<Seat> aisle = new ArrayList<>();
            for (int j = 0; j < hall.getSeatsPerAisle(); j++) {
                aisle.add(new Seat(i, j, STATUS_FREE));
            }
            seats.add(aisle);
        }
        for (Ticket t : session.getTickets()) {
            seats.get(t.getAisle()).set(t.getSeat(), new Seat(t));
        }
        return seats;
    }

    private void checkAvailableTime(SessionDto sessionDto) {
        CinemaHall cinemaHall = sessionDto.getCinemaHall();
        LocalDateTime sessionDateTime = sessionDto.getDateTime();
        List<LocalDateTime> sessionsTime = sessionRepository
                .findByCinemaHall(cinemaHall)
                .stream()
                .map(Session::getDateTime)
                .filter(x -> Math.abs(sessionDateTime.until(x, ChronoUnit.HOURS)) < SESSION_DURATION_HOURS)
                .collect(Collectors.toList());
        if (!sessionsTime.isEmpty()) {
            throw new InvalidSessionTime();
        }
    }
}
