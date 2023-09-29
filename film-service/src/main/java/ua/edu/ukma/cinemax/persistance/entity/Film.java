package ua.edu.ukma.cinemax.persistance.entity;

import lombok.*;
import org.hibernate.Hibernate;
import ua.edu.ukma.cinemax.commons.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "films")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Film extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(name = "release_year", nullable = false)
    private int releaseYear;
    private String description;
    @Column(name = "tmdb_id", nullable = false, unique = true)
    private Long tmdbId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Film film = (Film) o;
        return id != null && Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
