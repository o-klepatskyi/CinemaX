package ua.edu.ukma.cinemax.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.ukma.cinemax.persistance.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findByEmail(String email);
}
