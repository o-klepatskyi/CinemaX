package ua.edu.ukma.cinemax.persistance.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cinema_hall_id", "date", "time"})
})
public class Session extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHall;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @DateTimeFormat(iso=DateTimeFormat.ISO.TIME)
    private LocalTime time;
    @OneToMany(cascade=CascadeType.ALL)
    private List<Ticket> tickets;

    public LocalDateTime getDateTime() {
        if (date == null || time == null) return null;
        return LocalDateTime.of(date, time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Session session = (Session) o;
        return id != null && Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
