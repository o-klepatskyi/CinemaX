package ua.edu.ukma.cinemax.dto.converters.impl

import org.junit.jupiter.api.Test
import ua.edu.ukma.cinemax.dto.FilmDto
import ua.edu.ukma.cinemax.dto.converter.impl.FilmConverterImpl
import ua.edu.ukma.cinemax.persistance.entity.Film

class FilmConverterTest {

    private val instance = FilmConverterImpl()

    @Test
    fun `From DTO to Model`() {
        val filmDto = MockDTO.filmDto

        val film: Film = instance.createFrom(filmDto)

        assert(film.id == filmDto.id)
        assert(film.title == filmDto.title)
        assert(film.releaseYear == filmDto.releaseYear)
        assert(film.description == filmDto.description)
        assert(film.tmdbId == filmDto.tmdbId)
    }

    @Test
    fun `From Model to DTO`() {
        val film = MockDTO.film

        val filmDto: FilmDto = instance.createFrom(film)

        assert(filmDto.id == film.id)
        assert(filmDto.title == film.title)
        assert(filmDto.releaseYear == film.releaseYear)
        assert(filmDto.description == film.description)
        assert(filmDto.tmdbId == film.tmdbId)
    }

    @Test
    fun `From DTO to Model with null values`() {
        val filmDto = FilmDto(1, null, 1999, null, 2, "Test link")

        val film: Film = instance.createFrom(filmDto)

        assert(film.id == filmDto.id)
        assert(film.title == filmDto.title)
        assert(film.releaseYear == filmDto.releaseYear)
        assert(film.description == filmDto.description)
        assert(film.tmdbId == filmDto.tmdbId)
    }
}