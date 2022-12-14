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
import ua.edu.ukma.cinemax.dto.converter.impl.CinemaHallConverterImpl
import ua.edu.ukma.cinemax.dto.converter.impl.FilmConverterImpl
import ua.edu.ukma.cinemax.dto.converter.impl.OrderConverterImpl
import ua.edu.ukma.cinemax.dto.converter.impl.SessionConverterImpl
import ua.edu.ukma.cinemax.dto.converter.impl.TicketConverterImpl
import ua.edu.ukma.cinemax.dto.converter.impl.UserConverterImpl
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall
import ua.edu.ukma.cinemax.persistance.entity.Film
import ua.edu.ukma.cinemax.persistance.entity.Role
import ua.edu.ukma.cinemax.persistance.entity.Ticket
import ua.edu.ukma.cinemax.persistance.entity.User
import ua.edu.ukma.cinemax.persistance.repository.RoleRepository

class OrderConverterTest {

    private val passwordEncoder = Mockito.mock(PasswordEncoder::class.java)
    private val roleRepository = Mockito.mock(RoleRepository::class.java)
    private val userConverter = UserConverterImpl(passwordEncoder, roleRepository)
    private val cinemaHallConverter = CinemaHallConverterImpl()
    private val filmConverter = FilmConverterImpl()
    private val sessionConverter = SessionConverterImpl(cinemaHallConverter, filmConverter)
    private val tickerConvertor = TicketConverterImpl(userConverter, sessionConverter)
    private val instance = OrderConverterImpl(userConverter, tickerConvertor)

    @Test
    fun `From DTO to Model`() {
        val userDto = MockDTO.userDto
        val orderDto = MockDTO.orderDto

        `when`(passwordEncoder.encode(userDto.password)).thenReturn("Encoded password")
        `when`(roleRepository.findByName("ROLE_USER")).thenReturn(Role(1, "USER"))

        val order = instance.createFrom(orderDto)

        assert(order.id == orderDto.id)
        assert(order.user.id == orderDto.user.id)
        assert(order.tickets.size == orderDto.tickets.size)
        assert(order.tickets[0].id == orderDto.tickets[0].id)
        assert(order.orderTime == orderDto.orderTime)
    }

    @Test
    fun `From Model to DTO`() {
        val order = MockDTO.order
        val orderDto = instance.createFrom(order)

        assert(order.id == orderDto.id)
        assert(order.user.id == orderDto.user.id)
        assert(order.tickets.size == orderDto.tickets.size)
        assert(order.tickets[0].id == orderDto.tickets[0].id)
        assert(order.orderTime == orderDto.orderTime)
    }
}