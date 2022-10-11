package com.example.cinemax.service;

import com.example.cinemax.repository.impl.UserCredentials;
import lombok.NonNull;
import org.springframework.stereotype.Service;

public interface UserService {
    UserCredentials getCredentials(@NonNull String userLogin);
}
