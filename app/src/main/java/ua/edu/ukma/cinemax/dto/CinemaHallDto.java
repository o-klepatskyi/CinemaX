package ua.edu.ukma.cinemax.dto;

import lombok.*;
import ua.edu.ukma.cinemax.commons.AbstractDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class CinemaHallDto extends AbstractDto {
    private Long id;
    @Size(max = 50)
    @NotBlank
    private String name;
    @Size(max = 100)
    private String description;
    @Min(0)
    @NotNull
    private Integer aisles;
    @Min(0)
    @NotNull
    private Integer seatsPerAisle;
    private List<Integer> seats;
}
