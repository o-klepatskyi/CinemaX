package ua.edu.ukma.cinemax.dto.converter.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.SessionDto;
import ua.edu.ukma.cinemax.dto.TicketDto;
import ua.edu.ukma.cinemax.dto.converter.SessionConverter;
import ua.edu.ukma.cinemax.dto.converter.TicketConverter;
import ua.edu.ukma.cinemax.dto.converter.UserConverter;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;

@Component
@AllArgsConstructor
public class TicketConverterImpl implements TicketConverter {

    private final UserConverter userConverter;
    private final SessionConverter sessionConverter;

    @Override
    public Ticket createFrom(TicketDto dto) {
        Ticket entity = new Ticket();
        update(dto, entity);
        return entity;
    }

    @Override
    public TicketDto createFrom(Ticket entity) {
        TicketDto dto = new TicketDto();
        if (entity != null) {
            dto.setId(entity.getId());
            dto.setUser(userConverter.createFrom(entity.getUser()));
            dto.setFilmSession(sessionConverter.createFrom(entity.getFilmSession()));

        }
        return dto;
    }

    @Override
    public Ticket update(TicketDto dto, Ticket entity) {
        if (dto != null) {
            if (dto.getId() != null) {
                entity.setId(dto.getId());
            }
            if (dto.getUser() != null){
                entity.setUser(userConverter.createFrom(dto.getUser()));
            }
            if (dto.getFilmSession() != null){
                entity.setFilmSession(sessionConverter.createFrom(dto.getFilmSession()));
            }
        }
        return null;
    }
}
