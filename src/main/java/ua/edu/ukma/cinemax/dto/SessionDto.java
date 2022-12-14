package ua.edu.ukma.cinemax.dto;

import lombok.*;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SessionDto extends AbstractDto {

    private Long id;
    private FilmDto film;
    @ManyToOne
    private CinemaHallDto cinemaHall;
    private LocalDate date;
    private LocalTime time;
}
