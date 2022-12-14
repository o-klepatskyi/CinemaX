package ua.edu.ukma.cinemax.dto.converter;

import ua.edu.ukma.cinemax.dto.OrderDto;
import ua.edu.ukma.cinemax.dto.TicketDto;
import ua.edu.ukma.cinemax.dto.converter.GenericConverter;
import ua.edu.ukma.cinemax.persistance.entity.Order;
import ua.edu.ukma.cinemax.persistance.entity.Ticket;

public interface OrderConverter extends GenericConverter<OrderDto, Order> {
}
