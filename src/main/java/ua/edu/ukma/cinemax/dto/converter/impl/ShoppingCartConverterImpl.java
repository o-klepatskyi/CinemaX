package ua.edu.ukma.cinemax.dto.converter.impl;

import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.ShoppingCartDto;
import ua.edu.ukma.cinemax.dto.converter.ShoppingCartConverter;
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;

@Component
public class ShoppingCartConverterImpl implements ShoppingCartConverter {

    @Override
    public ShoppingCart createFrom(ShoppingCartDto dto) {
        ShoppingCart entity = new ShoppingCart();
        update(dto, entity);
        return entity;
    }

    @Override
    public ShoppingCartDto createFrom(ShoppingCart entity) {
        ShoppingCartDto dto = new ShoppingCartDto();
        if (entity != null) {
            dto.setId(entity.getId());
            dto.setTickets(entity.getTickets());
            dto.setUser(entity.getUser());
        }
        return dto;
    }

    @Override
    public ShoppingCart update(ShoppingCartDto dto, ShoppingCart entity) {
        return null;
    }
}
