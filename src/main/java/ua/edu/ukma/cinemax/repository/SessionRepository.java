package ua.edu.ukma.cinemax.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.ukma.cinemax.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("FROM Ticket t LEFT JOIN FETCH t.filmSession fs " +
            "LEFT JOIN FETCH fs.film f " +
            "WHERE f.id = :id AND fs.showTime BETWEEN :dayStart AND :dayEnd")
    List<Session> getAvailableSessions(@Param("id") Long filmId,
                                       @Param("dayStart") LocalDateTime start,
                                       @Param("dayEnd") LocalDateTime end);
}
