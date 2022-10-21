package ua.edu.ukma.cinemax.service.impl;

import ua.edu.ukma.cinemax.repository.UserRepository;
import ua.edu.ukma.cinemax.repository.impl.UserCredentials;
import ua.edu.ukma.cinemax.service.UserService;
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
