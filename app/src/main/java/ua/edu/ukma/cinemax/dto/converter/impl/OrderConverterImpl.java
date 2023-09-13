package ua.edu.ukma.cinemax.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.OrderDto;
import ua.edu.ukma.cinemax.dto.converter.OrderConverter;
import ua.edu.ukma.cinemax.dto.converter.TicketConverter;
import ua.edu.ukma.cinemax.dto.converter.UserConverter;
import ua.edu.ukma.cinemax.persistance.entity.Order;

@Component
@RequiredArgsConstructor
public class OrderConverterImpl implements OrderConverter {
    private final UserConverter userConverter;
    private final TicketConverter ticketConverter;

    @Override
    public Order createFrom(OrderDto dto) {
        Order entity = new Order();
        update(dto, entity);
        return entity;
    }

    @Override
    public OrderDto createFrom(Order entity) {
        OrderDto dto = new OrderDto();
        if (entity != null) {
            dto.setId(entity.getId());
            dto.setUser(userConverter.createFrom(entity.getUser()));
            dto.setOrderTime(entity.getOrderTime());
            dto.setTickets(ticketConverter.createFromEntities(entity.getTickets()));
        }
        return dto;
    }

    @Override
    public Order update(OrderDto dto, Order entity) {
        if (dto != null) {
            if (dto.getId() != null) {
                entity.setId(dto.getId());
            }
            if (dto.getUser() != null) {
                entity.setUser(userConverter.createFrom(dto.getUser()));
            }
            if (dto.getOrderTime() != null) {
                entity.setOrderTime(dto.getOrderTime());
            }
            if (dto.getTickets() != null) {
                entity.setTickets(ticketConverter.createFromDtos(dto.getTickets()));
            }
        }
        return null;
    }
}
