package ua.edu.ukma.cinemax.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
}
