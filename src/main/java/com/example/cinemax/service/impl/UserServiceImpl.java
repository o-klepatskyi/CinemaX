package com.example.cinemax.service.impl;

import com.example.cinemax.repository.UserRepository;
import com.example.cinemax.repository.impl.UserCredentials;
import com.example.cinemax.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    // field inject
    @Autowired
    private UserRepository userRepository;

    public UserCredentials getCredentials(@NonNull String userLogin) {
        return userRepository.select(userLogin);
    }
}
