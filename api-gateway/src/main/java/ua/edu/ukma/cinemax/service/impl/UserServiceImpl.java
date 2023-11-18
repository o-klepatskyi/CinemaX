package ua.edu.ukma.cinemax.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.dto.converter.UserConverter;
import ua.edu.ukma.cinemax.persistance.entity.User;
import ua.edu.ukma.cinemax.persistance.repository.UserRepository;
import ua.edu.ukma.cinemax.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public void add(UserDto userDto) {
        User user = userConverter.createFrom(userDto);
        userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserDto> getAll() {
        return userConverter.createFromEntities(userRepository.findAll());
    }

    @Override
    public void update(UserDto userDto) {
        User entityToUpdate = userRepository.findByUsername(userDto.getUsername());
        userConverter.update(userDto, entityToUpdate);
        userRepository.save(entityToUpdate);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
