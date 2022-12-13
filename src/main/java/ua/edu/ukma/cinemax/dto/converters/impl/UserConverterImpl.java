package ua.edu.ukma.cinemax.dto.converters.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.dto.converters.UserConverter;
import ua.edu.ukma.cinemax.persistance.model.Role;
import ua.edu.ukma.cinemax.persistance.model.User;
import ua.edu.ukma.cinemax.persistance.repository.RoleRepository;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverterImpl implements UserConverter {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User createFrom(UserDto dto) {
        User entity = new User();
        return update(dto, entity);
    }

    @Override
    public UserDto createFrom(User entity) {
        UserDto dto = new UserDto();
        if (entity != null) {
            dto.setId(entity.getId());
            dto.setEmail(entity.getEmail());
            dto.setUsername(entity.getUsername());
            dto.setPassword(entity.getPassword());
            dto.setRoles(entity.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        }
        return dto;
    }

    @Override
    public User update(UserDto dto, User entity) {
        if (dto != null && entity != null) {
            if (dto.getId() != null) {
                entity.setId(dto.getId());
            }
            if (dto.getEmail() != null) {
                entity.setEmail(dto.getEmail());
            }
            if (dto.getUsername() != null) {
                entity.setUsername(dto.getUsername());
            }
            if (dto.getPassword() != null) {
                entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            if (dto.getRoles() != null) {
                entity.setRoles(dto.getRoles().stream().map(roleRepository::findByName).collect(Collectors.toList()));
            }
        }
        return entity;
    }
}
