package ua.edu.ukma.cinemax.service.impl;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.dto.CinemaHallDto;
import ua.edu.ukma.cinemax.dto.converter.CinemaHallConverter;
import ua.edu.ukma.cinemax.exception.InvalidIDException;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.repository.CinemaHallRepository;
import ua.edu.ukma.cinemax.service.CinemaHallService;

@Service
@RequiredArgsConstructor
public class CinemaHallServiceImpl implements CinemaHallService {
    private final CinemaHallRepository cinemaHallRepository;
    private final CinemaHallConverter cinemaHallConverter;

    @Override
    public void add(CinemaHallDto cinemaHall) {
        cinemaHallRepository.save(cinemaHallConverter.createFrom(cinemaHall));
    }

    @Override
    public CinemaHall get(Long id) {
        Optional<CinemaHall> maybeFilm = cinemaHallRepository.findById(id);
        if (maybeFilm.isEmpty())
            throw new InvalidIDException("No cinema hall with id " + id, null);
        return maybeFilm.get();
    }

    @Override
    public List<CinemaHallDto> getAll() {
        return cinemaHallConverter.createFromEntities(cinemaHallRepository.findAll());
    }

    @Override
    public void update(CinemaHallDto cinemaHall) {
        CinemaHall entityToUpdate =
                cinemaHallRepository.getReferenceById(cinemaHall.getId());
        cinemaHallConverter.update(cinemaHall, entityToUpdate);
        cinemaHallRepository.save(entityToUpdate);
    }

    @Override
    public void delete(Long id) {
        cinemaHallRepository.deleteById(id);
    }
}
