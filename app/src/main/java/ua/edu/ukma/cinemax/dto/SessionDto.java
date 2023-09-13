package ua.edu.ukma.cinemax.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.Film;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionDto extends AbstractDto {
    private Long id;
    @NotNull
    private Film film;
    @NotNull
    private CinemaHall cinemaHall;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;

    public LocalDateTime getDateTime() {
        if (date == null || time == null) {
            return null;
        }
        return LocalDateTime.of(date, time);
    }

}
