package ua.edu.ukma.cinemax.dto.service

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import ua.edu.ukma.cinemax.dto.converter.impl.FilmConverterImpl
import ua.edu.ukma.cinemax.dto.converters.impl.MockDTO
import ua.edu.ukma.cinemax.persistance.repository.FilmRepository
import ua.edu.ukma.cinemax.service.FilmService
import ua.edu.ukma.cinemax.service.impl.FilmServiceImpl
import java.util.*

class FilmServiceTest {
    private val filmRepository = Mockito.mock(FilmRepository::class.java)
    private val filmConverter = FilmConverterImpl()

    private val instance = FilmServiceImpl(filmRepository, filmConverter)

    @Test
    fun `Add film`() {
        Mockito.`when`(filmRepository.save(MockDTO.film)).thenReturn(MockDTO.film)
        instance.add(MockDTO.filmDto)

        Mockito.verify(filmRepository, Mockito.times(1)).save(MockDTO.film)
    }

    @Test
    fun `Get film by id`() {
        Mockito.`when`(filmRepository.findById(MockDTO.film.id)).thenReturn(Optional.of(MockDTO.film))
        val film = instance.get(MockDTO.film.id)
        assert(film == MockDTO.film)

        Mockito.verify(filmRepository, Mockito.times(1)).findById(MockDTO.film.id)
    }

    @Test
    fun `Get all films`() {
        Mockito.`when`(filmRepository.findAll()).thenReturn(listOf(MockDTO.film))
        val films = instance.all
        assert(films.size == 1)

        Mockito.verify(filmRepository, Mockito.times(1)).findAll()
    }

    @Test
    fun `Update film`() {
        Mockito.`when`(filmRepository.getReferenceById(MockDTO.film.id)).thenReturn(MockDTO.film)
        Mockito.`when`(filmRepository.save(MockDTO.film)).thenReturn(MockDTO.film)
        instance.update(MockDTO.filmDto)

        Mockito.verify(filmRepository, Mockito.times(1)).save(MockDTO.film)
    }

    @Test
    fun `Delete film`() {
        instance.delete(MockDTO.film.id)

        Mockito.verify(filmRepository, Mockito.times(1)).deleteById(MockDTO.film.id)
    }
}