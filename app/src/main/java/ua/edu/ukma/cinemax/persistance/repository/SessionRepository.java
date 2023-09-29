package ua.edu.ukma.cinemax.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.Session;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("FROM Session s LEFT JOIN FETCH s.film f WHERE f.id = :id AND s.date >= :day")
    List<Session> getAvailableSessions(@Param("id") Long filmId,
                                       @Param("day") LocalDate day);

    List<Session> findByCinemaHall(CinemaHall cinemaHall);
}
