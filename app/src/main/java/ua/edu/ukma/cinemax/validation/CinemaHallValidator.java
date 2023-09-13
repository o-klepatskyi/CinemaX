package ua.edu.ukma.cinemax.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ua.edu.ukma.cinemax.dto.CinemaHallDto;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.repository.CinemaHallRepository;

@Component
@RequiredArgsConstructor
public class CinemaHallValidator implements Validator<CinemaHallDto, CinemaHall> {
    private final CinemaHallRepository cinemaHallRepository;

    @Override
    public void validateFieldConstraints(CinemaHallDto object, BindingResult result) {
        checkUniqueFieldConstraint(object, result, cinemaHallRepository,
                "name",
                "There is already a ciname hall with this name");
    }
}
