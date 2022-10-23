package ua.edu.ukma.cinemax.service.impl;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.model.User;
import ua.edu.ukma.cinemax.repository.UserRepository;
import ua.edu.ukma.cinemax.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User get(@NotNull String email) {
        return userRepository.get(email).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) {
        User userForUpdate = userRepository.getReferenceById(user.getId());
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setPassword(user.getPassword());
        userRepository.save(userForUpdate);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
