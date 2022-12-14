package ua.edu.ukma.cinemax.persistance.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "sessions")
@Data
@NoArgsConstructor
public class Session extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Film film;
    @ManyToOne
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHall;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime time;
}
