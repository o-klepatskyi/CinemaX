package ua.edu.ukma.cinemax.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.entity.Session;

@Data
@NoArgsConstructor
public class ApiSession {
    private Long id;
    private Film film;
    private CinemaHall cinemaHall;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @DateTimeFormat(iso=DateTimeFormat.ISO.TIME)
    private LocalTime time;

    public ApiSession(Session session) {
        this.id = session.getId();
        this.film = session.getFilm();
        this.cinemaHall = session.getCinemaHall();
        this.date = session.getDate();
        this.time = session.getTime();
    }

    public Session toModel() {
        Session model = new Session();
        model.setId(id);
        model.setFilm(film);
        model.setCinemaHall(cinemaHall);
        model.setDate(date);
        model.setTime(time);
        return model;
    }
}
