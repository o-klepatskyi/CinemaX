package ua.edu.ukma.cinemax.dto.converter;

import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.ShoppingCartDto;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart;

public interface ShoppingCartConverter extends GenericConverter<ShoppingCartDto, ShoppingCart> {
}
