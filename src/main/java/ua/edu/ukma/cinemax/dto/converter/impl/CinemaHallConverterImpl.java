package ua.edu.ukma.cinemax.dto.converter.impl;

import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.CinemaHallDto;
import ua.edu.ukma.cinemax.dto.converter.CinemaHallConverter;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;

@Component
public class CinemaHallConverterImpl implements CinemaHallConverter {
    @Override
    public CinemaHall createFrom(CinemaHallDto dto) {
        CinemaHall entity = new CinemaHall();
        update(dto, entity);
        return entity;
    }

    @Override
    public CinemaHallDto createFrom(CinemaHall entity) {
        CinemaHallDto dto = new CinemaHallDto();
        if (entity != null) {
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setAisles(entity.getAisles());
            dto.setSeatsPerAisle(entity.getSeatsPerAisle());
        }
        return dto;
    }

    @Override
    public CinemaHall update(CinemaHallDto dto, CinemaHall entity) {
        if (dto != null && entity != null) {
            if (dto.getId() != null) {
                entity.setId(dto.getId());
            }
            if (dto.getName() != null) {
                entity.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                entity.setDescription(dto.getDescription());
            }
            if (dto.getAisles() != null) {
                entity.setAisles(dto.getAisles());
            }
            if (dto.getSeatsPerAisle() != null) {
                entity.setSeatsPerAisle(dto.getSeatsPerAisle());
            }
        }
        return entity;
    }
}
