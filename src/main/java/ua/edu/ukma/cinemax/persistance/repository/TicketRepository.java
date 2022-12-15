package ua.edu.ukma.cinemax.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("Select t from Ticket t where " +
            "t.user.id = :id and t.isBought = false")
    List<Ticket> getTicketsInShoppingCart(@Param("id") Long id);


    @Modifying
    @Query("UPDATE Ticket t SET t.user = null, t.filmSession = null")
    void deleteTicket(@Param("id") Long id);
}
