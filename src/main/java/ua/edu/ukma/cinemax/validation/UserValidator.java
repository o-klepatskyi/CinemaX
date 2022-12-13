package ua.edu.ukma.cinemax.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.persistance.entity.User;
import ua.edu.ukma.cinemax.persistance.repository.UserRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator<UserDto> {
    private final UserRepository userRepository;

    @Override
    public void validateFieldConstraints(UserDto object, BindingResult result) {
        User existing = userRepository.findByUsername(object.getUsername());
        if (existing != null && !Objects.equals(object.getId(), existing.getId())) {
            result.rejectValue("username", null,
                    "There is already an account registered with that username");
        }
        existing = userRepository.findByEmail(object.getEmail());
        if (existing != null && !Objects.equals(object.getId(), existing.getId())) {
            result.rejectValue("email", null,
                    "There is already an account registered with that email");
        }
    }
}
