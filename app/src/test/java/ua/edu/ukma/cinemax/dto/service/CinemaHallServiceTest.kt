package ua.edu.ukma.cinemax.dto.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import ua.edu.ukma.cinemax.dto.converter.impl.CinemaHallConverterImpl
import ua.edu.ukma.cinemax.dto.converters.impl.MockDTO
import ua.edu.ukma.cinemax.commons.exception.InvalidIDException
import ua.edu.ukma.cinemax.persistance.repository.CinemaHallRepository
import ua.edu.ukma.cinemax.service.impl.CinemaHallServiceImpl
import java.util.*

class CinemaHallServiceTest {
    private val cinemaHallRepository = mock(CinemaHallRepository::class.java)
    private val cinemaHallConverter = CinemaHallConverterImpl()

    private val instance = CinemaHallServiceImpl(cinemaHallRepository, cinemaHallConverter)

    @Test
    fun `Add Cinema Hall`() {
        `when`(cinemaHallRepository.save(MockDTO.cinema)).thenReturn(MockDTO.cinema)
        instance.add(MockDTO.cinemaHallDto)

        verify(cinemaHallRepository, times(1)).save(MockDTO.cinema)
    }

    @Test
    fun `Get Cinema Hall By Id`() {
        `when`(cinemaHallRepository.findById(MockDTO.cinema.id)).thenReturn(Optional.of(MockDTO.cinema))
        instance.get(MockDTO.cinema.id)

        verify(cinemaHallRepository, times(1)).findById(MockDTO.cinema.id)
    }

    @Test
    fun `Get Cinema Hall By Id Error`() {
        `when`(cinemaHallRepository.findById(MockDTO.cinema.id)).thenReturn(Optional.empty())
        assertThrows<ua.edu.ukma.cinemax.commons.exception.InvalidIDException> {
            instance.get(MockDTO.cinema.id)

        }
        verify(cinemaHallRepository, times(1)).findById(MockDTO.cinema.id)
    }

    @Test
    fun `Get All Cinema Halls`() {
        `when`(cinemaHallRepository.findAll()).thenReturn(listOf(MockDTO.cinema))
        instance.all
        verify(cinemaHallRepository, times(1)).findAll()
    }

    @Test
    fun `Update Cinema Hall`() {
        `when`(cinemaHallRepository.save(MockDTO.cinema)).thenReturn(MockDTO.cinema)
        `when`(cinemaHallRepository.getReferenceById(MockDTO.cinema.id)).thenReturn(MockDTO.cinema)
        instance.update(MockDTO.cinemaHallDto)

        verify(cinemaHallRepository, times(1)).save(MockDTO.cinema)
    }

    @Test
    fun `Delete Cinema Hall`() {
        instance.delete(MockDTO.cinema.id)
        verify(cinemaHallRepository, times(1)).deleteById(MockDTO.cinema.id)
    }
}