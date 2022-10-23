package ua.edu.ukma.cinemax.service;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import ua.edu.ukma.cinemax.model.User;

public interface UserService {
    User add(User user);

    User get(Long id);

    User get(@NotNull String email);

    List<User> getAll();

    void update(User user);

    void delete(Long id);
}
