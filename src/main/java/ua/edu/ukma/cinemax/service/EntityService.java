package ua.edu.ukma.cinemax.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.dto.AbstractDto;
import ua.edu.ukma.cinemax.persistance.entity.AbstractEntity;

@RequiredArgsConstructor
public abstract class EntityService<D extends AbstractDto, E extends AbstractEntity> {

}
