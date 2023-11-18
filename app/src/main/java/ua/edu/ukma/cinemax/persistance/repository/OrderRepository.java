package ua.edu.ukma.cinemax.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ukma.cinemax.persistance.entity.Order;
import ua.edu.ukma.cinemax.persistance.entity.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("FROM Order o "
            + "LEFT JOIN FETCH o.tickets t "
            + "LEFT JOIN FETCH t.filmSession fs "
            + "LEFT JOIN FETCH fs.film "
            + "LEFT JOIN FETCH fs.cinemaHall "
            + "WHERE o.user = :user")
    List<Order> getByUser(User user);
}
