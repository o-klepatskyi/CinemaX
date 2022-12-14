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

class TIcketConverterTest {

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
        val ticketDto = MockDTO.ticketDto

        `when`(passwordEncoder.encode( MockDTO.userDto.password)).thenReturn("Encoded password")
        `when`(roleRepository.findByName("ROLE_USER")).thenReturn(Role(1, "USER"))

        val ticker = tickerConvertor.createFrom(ticketDto)

        assert(ticker.id == ticketDto.id)
        assert(ticker.user.id == ticketDto.user.id)
    }

    @Test
    fun `From Model to DTO`() {
        val ticket = MockDTO.ticket
        val ticketDto = tickerConvertor.createFrom(ticket)

        assert(ticket.id == ticketDto.id)
        assert(ticket.user.id == ticketDto.user.id)
    }
}