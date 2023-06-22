package ua.edu.ukma.cinemax.service;

import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.persistance.entity.User;

import java.util.List;

public interface UserService {
    void add(UserDto user);

    User get(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

    List<UserDto> getAll();

    void update(UserDto user);

    void delete(Long id);
}
