package ua.edu.ukma.cinemax.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.persistance.entity.User;
import ua.edu.ukma.cinemax.persistance.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator<UserDto, User> {
    private final UserRepository userRepository;

    @Override
    public void validateFieldConstraints(UserDto object, BindingResult result) {
        checkUniqueFieldConstraint(object, result, userRepository,
                "username",
                "There is already an account registered with that username");
        checkUniqueFieldConstraint(object, result, userRepository,
                "email",
                "There is already an account registered with that email");
    }
}
