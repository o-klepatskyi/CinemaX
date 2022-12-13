package ua.edu.ukma.cinemax.dto.converters.impl;

import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.converters.FilmConverter;
import ua.edu.ukma.cinemax.persistance.entity.Film;

@Component
public class FilmConverterImpl implements FilmConverter {

    @Override
    public Film createFrom(FilmDto dto) {
        Film entity = new Film();
        update(dto, entity);
        return entity;
    }

    @Override
    public FilmDto createFrom(Film entity) {
        FilmDto dto = new FilmDto();
        if (entity != null) {
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setReleaseYear(entity.getReleaseYear());
            dto.setDescription(entity.getDescription());
            dto.setTmdbId(entity.getTmdbId());
        }
        return dto;
    }

    @Override
    public Film update(FilmDto dto, Film entity) {
        if (dto != null && entity != null) {
            if (dto.getId() != null) {
                entity.setId(dto.getId());
            }
            if (dto.getTitle() != null){
                entity.setTitle(dto.getTitle());
            }
            if (dto.getReleaseYear() != null){
                entity.setReleaseYear(dto.getReleaseYear());
            }
            if (dto.getDescription() != null){
                entity.setDescription(dto.getDescription());
            }
            if (dto.getTmdbId() != null){
                entity.setTmdbId(dto.getTmdbId());
            }
        }
        return entity;
    }
}
