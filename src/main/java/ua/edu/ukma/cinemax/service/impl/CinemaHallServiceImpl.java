package ua.edu.ukma.cinemax.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.repository.CinemaHallRepository;
import ua.edu.ukma.cinemax.service.CinemaHallService;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    private CinemaHallRepository cinemaHallRepository;

    @Autowired
    public CinemaHallServiceImpl(CinemaHallRepository cinemaHallRepository) {
        this.cinemaHallRepository = cinemaHallRepository;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallRepository.save(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallRepository.getReferenceById(id);
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallRepository.findAll();
    }

    @Override
    public void update(CinemaHall cinemaHall) {
        CinemaHall cinemaHallForUpdate =
                cinemaHallRepository.getReferenceById(cinemaHall.getId());
        cinemaHallForUpdate.setCapacity(cinemaHall.getCapacity());
        cinemaHallForUpdate.setDescription(cinemaHall.getDescription());
        cinemaHallRepository.save(cinemaHallForUpdate);
    }

    @Override
    public void delete(Long id) {
        cinemaHallRepository.deleteById(id);
    }
}
