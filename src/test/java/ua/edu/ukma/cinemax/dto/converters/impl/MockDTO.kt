package ua.edu.ukma.cinemax.dto.converters.impl

import ua.edu.ukma.cinemax.dto.*
import ua.edu.ukma.cinemax.persistance.entity.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object MockDTO {
    val userDto = UserDto(1, "Test name", "Test email", "Test password", listOf("ROLE_USER"))
    val user = User(1, "Test name", "Test email", "Test password", true, listOf(Role(1, "USER")))

    val filmDto = FilmDto(1, "Test name", 1999, "Test desc", 2, "Test link")
    val film = Film(1, "Test name", 1999, "Test desc", 2)

//    val cinemaHallDto = CinemaHallDto(1, "Test name", "Test desc", 2, 3)
//    val cinema = CinemaHall(1, "Test name", "Test desc", 2, 3)
//
//    val sessionDto = SessionDto(1, filmDto, cinemaHallDto, LocalDate.MAX, LocalTime.MAX)
//    val session = Session(1, film, cinema, LocalDate.MAX, LocalTime.MAX)
//
//    val ticketDto = TicketDto(1, userDto, sessionDto)
//    val ticket = Ticket(1, user, session)

//    val orderDto = OrderDto(1, userDto, listOf(ticketDto), LocalDateTime.MAX)
//    val order = Order(1, user, listOf(ticket), LocalDateTime.MAX)
//
//    val shoppingCartDto = ShoppingCartDto(1, listOf(ticketDto), userDto)
//    val shoppingCart = ShoppingCart(1, listOf(ticket), user)
}