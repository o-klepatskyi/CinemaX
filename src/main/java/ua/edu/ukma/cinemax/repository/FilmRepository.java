package ua.edu.ukma.cinemax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.cinemax.model.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
