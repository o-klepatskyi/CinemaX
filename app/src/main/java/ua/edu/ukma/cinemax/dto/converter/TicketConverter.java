package ua.edu.ukma.cinemax.dto.converter;

import ua.edu.ukma.cinemax.commons.GenericConverter;
import ua.edu.ukma.cinemax.dto.TicketDto;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;

public interface TicketConverter extends GenericConverter<TicketDto, Ticket> {
}
