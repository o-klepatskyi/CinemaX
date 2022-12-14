package ua.edu.ukma.cinemax.dto.converters.impl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.security.crypto.password.PasswordEncoder
import ua.edu.ukma.cinemax.dto.CinemaHallDto
import ua.edu.ukma.cinemax.dto.FilmDto
import ua.edu.ukma.cinemax.dto.OrderDto
import ua.edu.ukma.cinemax.dto.SessionDto
import ua.edu.ukma.cinemax.dto.TicketDto
import ua.edu.ukma.cinemax.dto.UserDto
import ua.edu.ukma.cinemax.dto.converter.SessionConverter
import ua.edu.ukma.cinemax.dto.converter.impl.*
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall
import ua.edu.ukma.cinemax.persistance.entity.Film
import ua.edu.ukma.cinemax.persistance.entity.Role
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart
import ua.edu.ukma.cinemax.persistance.entity.Ticket
import ua.edu.ukma.cinemax.persistance.entity.User
import ua.edu.ukma.cinemax.persistance.repository.RoleRepository

class ShoppingCartConverterTest {

    private val passwordEncoder = Mockito.mock(PasswordEncoder::class.java)
    private val roleRepository = Mockito.mock(RoleRepository::class.java)
    private val userConverter = UserConverterImpl(passwordEncoder, roleRepository)
    private val cinemaHallConverter = CinemaHallConverterImpl()
    private val filmConverter = FilmConverterImpl()
    private val sessionConverter = SessionConverterImpl(cinemaHallConverter, filmConverter)
    private val tickerConvertor = TicketConverterImpl(userConverter, sessionConverter)
    private val shoppingCartConvert = ShoppingCartConverterImpl(tickerConvertor, userConverter)

    @Test
    fun `From DTO to Model`() {
        val shoppingCartDto = MockDTO.shoppingCartDto

        `when`(passwordEncoder.encode( MockDTO.userDto.password)).thenReturn("Encoded password")
        `when`(roleRepository.findByName("ROLE_USER")).thenReturn(Role(1, "USER"))

        val shoppingCart = shoppingCartConvert.createFrom(shoppingCartDto)

        assert(shoppingCart.id == shoppingCartDto.id)
        assert(shoppingCart.tickets.size == shoppingCartDto.tickets.size)
        assert(shoppingCart.user.id == shoppingCartDto.user.id)
    }

    @Test
    fun `From Model to DTO`() {
        val shoppingCart = MockDTO.shoppingCart
        val shoppingCartDto = shoppingCartConvert.createFrom(shoppingCart)

        assert(shoppingCart.id == shoppingCartDto.id)
        assert(shoppingCart.tickets.size == shoppingCartDto.tickets.size)
        assert(shoppingCart.user.id == shoppingCartDto.user.id)
    }
}