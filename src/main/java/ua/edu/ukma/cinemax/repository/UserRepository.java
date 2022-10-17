package ua.edu.ukma.cinemax.repository;

import ua.edu.ukma.cinemax.repository.impl.UserCredentials;
import lombok.NonNull;

public interface UserRepository {
    // select user by login
    UserCredentials select(@NonNull String login);
}
