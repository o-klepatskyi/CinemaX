package ua.edu.ukma.cinemax.service;

import java.util.List;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;

public interface CinemaHallService {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();

    void update(CinemaHall cinemaHall);

    void delete(Long id);
}
