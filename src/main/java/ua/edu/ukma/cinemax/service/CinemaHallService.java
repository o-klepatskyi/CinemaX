package ua.edu.ukma.cinemax.service;

import java.util.List;

import ua.edu.ukma.cinemax.dto.CinemaHallDto;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;

public interface CinemaHallService {
    void add(CinemaHallDto cinemaHall);
    CinemaHall get(Long id);
    List<CinemaHallDto> getAll();
    void update(CinemaHallDto cinemaHall);
    void delete(Long id);
}
