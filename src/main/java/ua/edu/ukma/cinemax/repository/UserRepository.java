package ua.edu.ukma.cinemax.repository;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.ukma.cinemax.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User u WHERE u.email = :email")
    Optional<User> get(@NotNull @Param("email") String email);
}
