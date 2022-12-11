package ua.edu.ukma.cinemax.persistance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "films")
@Data
@NoArgsConstructor
public class Film extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @Column(name = "release_year")
    private int releaseYear;
    private String description;
    @Column(name = "tmdb_id")
    private Long tmdbId;
}
