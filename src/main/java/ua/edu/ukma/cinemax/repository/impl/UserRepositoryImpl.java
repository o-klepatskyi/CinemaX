package ua.edu.ukma.cinemax.repository.impl;

import ua.edu.ukma.cinemax.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

    @Override
    public UserCredentials select(@NonNull String login) {
        // return default user for now
        return new UserCredentials(login, "12345");
    }
}
