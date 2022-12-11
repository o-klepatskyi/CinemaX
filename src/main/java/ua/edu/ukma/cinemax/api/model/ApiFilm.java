package ua.edu.ukma.cinemax.api.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ua.edu.ukma.cinemax.persistance.model.Film;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiFilm {
    @Id
    private Long id;
    @Length(max=100)
    @NotBlank
    private String title;
    @Min(1900)
    private Integer releaseYear;
    @Length(max=200)
    private String description;
    private Long tmdbId;

    public ApiFilm(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
        this.description = film.getDescription();
        this.tmdbId = film.getTmdbId();
    }

    public Film toModel() {
        Film model = new Film();
        model.setId(id);
        model.setTitle(title);
        model.setReleaseYear(releaseYear);
        model.setDescription(description);
        model.setTmdbId(tmdbId);
        return model;
    }
}
