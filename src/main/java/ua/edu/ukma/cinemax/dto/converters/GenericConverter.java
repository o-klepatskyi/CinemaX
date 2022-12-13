package ua.edu.ukma.cinemax.dto.converters;

import ua.edu.ukma.cinemax.dto.AbstractDto;
import ua.edu.ukma.cinemax.persistance.entity.AbstractEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface GenericConverter<D extends AbstractDto, E extends AbstractEntity> {

    E createFrom(D dto);

    D createFrom(E entity);

    E update(D dto, E entity);

    default List<D> createFromEntities(final Collection<E> entities) {
        return entities.stream()
                .map(this::createFrom)
                .collect(Collectors.toList());
    }

    default List<E> createFromDtos(final Collection<D> dtos) {
        return dtos.stream()
                .map(this::createFrom)
                .collect(Collectors.toList());
    }

}
