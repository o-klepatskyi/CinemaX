package ua.edu.ukma.cinemax.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JmsFilmResponse {
    private boolean success;
    private FilmDto filmDto;
    private List<FilmDto> filmDtoList;
    private String errorMessage;
}
