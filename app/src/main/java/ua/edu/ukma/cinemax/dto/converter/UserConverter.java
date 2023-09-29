package ua.edu.ukma.cinemax.dto.converter;

import ua.edu.ukma.cinemax.commons.GenericConverter;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.persistance.entity.User;

public interface UserConverter extends GenericConverter<UserDto, User> {
}
