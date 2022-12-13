package ua.edu.ukma.cinemax.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.repository.FilmRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FilmValidator implements Validator<FilmDto> {
    private final FilmRepository filmRepository;

    @Override
    public void validateFieldConstraints(FilmDto object, BindingResult result) {
        Film existing = filmRepository.findByTmdbId(object.getTmdbId());
        if (existing != null && !Objects.equals(object.getId(), existing.getId())) {
            result.rejectValue("tmdbId", null,
                    "There is already a film with this tmdbId");
        }
    }
}
