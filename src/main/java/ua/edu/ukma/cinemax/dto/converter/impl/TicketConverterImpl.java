package ua.edu.ukma.cinemax.dto.converter.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.TicketDto;
import ua.edu.ukma.cinemax.dto.converter.TicketConverter;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;

@Component
@AllArgsConstructor
public class TicketConverterImpl implements TicketConverter {

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
            dto.setUser(entity.getUser());
            dto.setFilmSession(entity.getFilmSession());
            dto.setAisle(entity.getAisle());
            dto.setSeat(entity.getSeat());
            dto.setIsBought(entity.getIsBought());
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
                entity.setUser(dto.getUser());
            }
            if (dto.getFilmSession() != null){
                entity.setFilmSession(dto.getFilmSession());
            }
            if (dto.getAisle() != null){
                entity.setAisle(dto.getAisle());
            }
            if (dto.getSeat() != null){
                entity.setSeat(dto.getSeat());
            }
            if (dto.getIsBought() != null){
                entity.setIsBought(dto.getIsBought());
            }
        }
        return null;
    }
}
