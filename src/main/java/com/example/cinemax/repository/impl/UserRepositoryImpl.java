package com.example.cinemax.repository.impl;

import com.example.cinemax.repository.UserRepository;
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
