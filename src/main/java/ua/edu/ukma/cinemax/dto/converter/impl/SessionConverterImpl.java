package ua.edu.ukma.cinemax.dto.converter.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.CinemaHallDto;
import ua.edu.ukma.cinemax.dto.SessionDto;
import ua.edu.ukma.cinemax.dto.converter.CinemaHallConverter;
import ua.edu.ukma.cinemax.dto.converter.FilmConverter;
import ua.edu.ukma.cinemax.dto.converter.SessionConverter;
import ua.edu.ukma.cinemax.dto.converter.TicketConverter;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;

@Component
@AllArgsConstructor
public class SessionConverterImpl implements SessionConverter {

    private final CinemaHallConverter cinemaHallConverter;
    private final FilmConverter filmConverter;
    @Override
    public Session createFrom(SessionDto dto) {
        Session entity = new Session();
        update(dto, entity);
        return entity;
    }

    @Override
    public SessionDto createFrom(Session entity) {
        SessionDto dto = new SessionDto();
        if (entity != null) {
            dto.setId(entity.getId());
            dto.setFilm(entity.getFilm());
            dto.setCinemaHall(entity.getCinemaHall());
            dto.setDate(entity.getDate());
            dto.setTime(entity.getTime());
        }
        return dto;
    }

    @Override
    public Session update(SessionDto dto, Session entity) {
        if (dto != null) {
            if (dto.getId() != null) {
                entity.setId(dto.getId());
            }
            if (dto.getFilm() != null){
                entity.setFilm(dto.getFilm());
            }
            if (dto.getCinemaHall() != null){
                entity.setCinemaHall(dto.getCinemaHall());
            }
            if (dto.getDate() != null){
                entity.setDate(dto.getDate());
            }
            if (dto.getTime() != null){
                entity.setTime(dto.getTime());
            }
        }
        return null;
    }
}
