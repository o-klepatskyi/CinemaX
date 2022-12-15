package ua.edu.ukma.cinemax.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;

import static ua.edu.ukma.cinemax.dto.TicketStatus.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private int row;
    private int column;
    private int status = STATUS_FREE;

    public Seat(Ticket ticket) {
        row = ticket.getAisle();
        column = ticket.getSeat();
        status = ticket.getIsBought() ? STATUS_BOUGHT : STATUS_RESERVED;
    }
}
