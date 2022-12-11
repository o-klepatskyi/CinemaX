package ua.edu.ukma.cinemax.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.cinemax.persistance.model.CinemaHall;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
}
