package ua.edu.ukma.cinemax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.cinemax.model.CinemaHall;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
}
