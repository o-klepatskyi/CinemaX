package ua.edu.ukma.cinemax.dto.converter.impl;

import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.SessionDto;
import ua.edu.ukma.cinemax.dto.converter.SessionConverter;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.entity.Session;

@Component
public class SessionConverterImpl implements SessionConverter {
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
        if (dto != null && entity != null) {
            if (dto.getId() != null) {
                entity.setId(dto.getId());
            }
            if (dto.getFilm() != null) {
                entity.setFilm(dto.getFilm());
            }
            if (dto.getCinemaHall() != null) {
                entity.setCinemaHall(dto.getCinemaHall());
            }
            if (dto.getDate() != null) {
                entity.setDate(dto.getDate());
            }
            if (dto.getTime() != null) {
                entity.setTime(dto.getTime());
            }
        }
        return entity;
    }
}
