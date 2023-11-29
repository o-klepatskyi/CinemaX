package ua.edu.ukma.cinemax.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JmsFilmMessage {
    private String method;
    private Long id;
    private FilmDto filmDto;
}
