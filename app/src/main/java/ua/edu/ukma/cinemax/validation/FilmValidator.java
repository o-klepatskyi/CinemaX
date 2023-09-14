//package ua.edu.ukma.cinemax.validation;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.BindingResult;
//import ua.edu.ukma.cinemax.dto.FilmDto;
//import ua.edu.ukma.cinemax.persistance.entity.Film;
//import ua.edu.ukma.cinemax.persistance.repository.FilmRepository;
//
//@Component
//@RequiredArgsConstructor
//public class FilmValidator implements Validator<FilmDto, Film> {
//    private final FilmRepository filmRepository;
//
//    @Override
//    public void validateFieldConstraints(FilmDto object, BindingResult result) {
//        checkUniqueFieldConstraint(object, result, filmRepository,
//                "tmdbId",
//                "There is already a film with this tmdbId");
//    }
//}
