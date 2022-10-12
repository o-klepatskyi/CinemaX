package com.example.cinemax.repository;

import com.example.cinemax.repository.impl.UserCredentials;
import lombok.NonNull;

public interface UserRepository {
    // select user by login
    UserCredentials select(@NonNull String login);
}
