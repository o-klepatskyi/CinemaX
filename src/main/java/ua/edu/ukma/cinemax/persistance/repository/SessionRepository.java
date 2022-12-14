package ua.edu.ukma.cinemax.persistance.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.ukma.cinemax.persistance.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("FROM Ticket t LEFT JOIN FETCH t.filmSession fs " +
            "LEFT JOIN FETCH fs.film f " +
            "WHERE f.id = :id AND fs.date = :day")
    List<Session> getAvailableSessions(@Param("id") Long filmId,
                                       @Param("day") Date day);
}
