package ua.edu.ukma.cinemax.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import ua.edu.ukma.cinemax.model.Session;
import ua.edu.ukma.cinemax.repository.SessionRepository;
import ua.edu.ukma.cinemax.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    private static final LocalTime END_OF_DAY = LocalTime.of(23, 59, 59);
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session add(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session get(Long id) {
        return sessionRepository.getReferenceById(id);
    }

    @Override
    public List<Session> get() {return sessionRepository.findAll();}

    @Override
    public List<Session> getAvailableSessions(Long filmId, LocalDate date) {
        return sessionRepository.getAvailableSessions(filmId, date.atStartOfDay(), date.atTime(END_OF_DAY));
    }

    @Override
    public void update(Session session) {
        Session sessionForUpdate = sessionRepository.getReferenceById(session.getId());
        sessionForUpdate.setFilm(session.getFilm());
        sessionForUpdate.setCinemaHall(session.getCinemaHall());
        sessionForUpdate.setShowTime(session.getShowTime());
        sessionRepository.save(sessionForUpdate);
    }

    @Override
    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }
}
