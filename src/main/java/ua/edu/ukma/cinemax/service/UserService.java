package ua.edu.ukma.cinemax.service;

import java.util.List;

import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.persistance.model.User;

public interface UserService {
    void add(UserDto user);
    User get(Long id);
    User get(String email);
    List<UserDto> getAll();
    void update(UserDto user);
    void delete(Long id);
}
