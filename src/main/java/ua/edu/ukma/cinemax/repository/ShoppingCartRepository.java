package ua.edu.ukma.cinemax.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ukma.cinemax.model.ShoppingCart;
import ua.edu.ukma.cinemax.model.User;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("FROM ShoppingCart sc "
            + "LEFT JOIN FETCH sc.tickets t "
            + "LEFT JOIN FETCH t.filmSession fs "
            + "LEFT JOIN FETCH fs.film "
            + "LEFT JOIN FETCH fs.cinemaHall "
            + "WHERE sc.user =:user")
    Optional<ShoppingCart> getByUser(User user);
}
