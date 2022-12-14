package ua.edu.ukma.cinemax.dto.converter;

import ua.edu.ukma.cinemax.dto.SessionDto;
import ua.edu.ukma.cinemax.dto.ShoppingCartDto;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;

public interface SessionConverter extends GenericConverter<SessionDto, Session> {
}
