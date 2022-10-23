package ua.edu.ukma.cinemax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.cinemax.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
