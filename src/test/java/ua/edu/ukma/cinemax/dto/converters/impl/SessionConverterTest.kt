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

class SessionConverterTest {

    private val passwordEncoder = Mockito.mock(PasswordEncoder::class.java)
    private val roleRepository = Mockito.mock(RoleRepository::class.java)
    private val userConverter = UserConverterImpl(passwordEncoder, roleRepository)
    private val cinemaHallConverter = CinemaHallConverterImpl()
    private val filmConverter = FilmConverterImpl()
    private val sessionConverter = SessionConverterImpl(cinemaHallConverter, filmConverter)
    private val tickerConvertor = TicketConverterImpl(userConverter, sessionConverter)
    private val orderConvert = OrderConverterImpl(userConverter, tickerConvertor)

    @Test
    fun `From DTO to Model`() {
        val sessionDto = MockDTO.sessionDto

        `when`(passwordEncoder.encode( MockDTO.userDto.password)).thenReturn("Encoded password")
        `when`(roleRepository.findByName("ROLE_USER")).thenReturn(Role(1, "USER"))

        val session = sessionConverter.createFrom(sessionDto)

        assert(session.id == sessionDto.id)
        assert(session.date == sessionDto.date)
        assert(session.time == sessionDto.time)
        assert(session.cinemaHall.id == sessionDto.cinemaHall.id)
    }

    @Test
    fun `From Model to DTO`() {
        val session = MockDTO.sessionDto
        val sessionDto = sessionConverter.createFrom(session)

        assert(session.id == sessionDto.id)
        assert(session.date == sessionDto.date)
        assert(session.time == sessionDto.time)
        assert(session.film.tmdbId == sessionDto.film.tmdbId)
    }
}