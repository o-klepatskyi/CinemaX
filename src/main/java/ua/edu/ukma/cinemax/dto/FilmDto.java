package ua.edu.ukma.cinemax.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto extends AbstractDto {
    private Long id;
    @NotBlank
    @Size(max=50)
    private String title;
    @Min(1900)
    @NotNull
    private Integer releaseYear;
    @Size(max=100)
    private String description;
    @Min(0)
    @NotNull
    private Long tmdbId;
}
