package ua.edu.ukma.cinemax.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.cinemax.persistance.entity.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
