package ua.edu.ukma.cinemax.api.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.entity.Session;

@Data
@NoArgsConstructor
public class ApiSession {
    private Long id;
    private Film film;
    private CinemaHall cinemaHall;
    private LocalDateTime showTime;

    public ApiSession(Session session) {
        this.id = session.getId();
        this.film = session.getFilm();
        this.cinemaHall = session.getCinemaHall();
        this.showTime = session.getShowTime();
    }

    public Session toModel() {
        Session model = new Session();
        model.setId(id);
        model.setFilm(film);
        model.setCinemaHall(cinemaHall);
        model.setShowTime(showTime);
        return model;
    }
}
