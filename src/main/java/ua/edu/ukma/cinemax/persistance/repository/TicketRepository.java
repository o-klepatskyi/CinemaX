package ua.edu.ukma.cinemax.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
