package ua.edu.ukma.cinemax.validation;

import org.springframework.validation.BindingResult;
import ua.edu.ukma.cinemax.dto.AbstractDto;

public interface Validator <D extends AbstractDto> {
    void validateFieldConstraints(D object, BindingResult result);
}
