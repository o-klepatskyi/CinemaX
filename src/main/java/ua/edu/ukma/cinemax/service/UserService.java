package ua.edu.ukma.cinemax.service;

import ua.edu.ukma.cinemax.repository.impl.UserCredentials;
import lombok.NonNull;

public interface UserService {
    UserCredentials getCredentials(@NonNull String userLogin);
}
