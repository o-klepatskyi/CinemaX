package ua.edu.ukma.cinemax.dto.service

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import ua.edu.ukma.cinemax.dto.converter.CinemaHallConverter
import ua.edu.ukma.cinemax.dto.converter.SessionConverter
import ua.edu.ukma.cinemax.dto.converters.impl.MockDTO
import ua.edu.ukma.cinemax.persistance.repository.SessionRepository
import ua.edu.ukma.cinemax.service.impl.SessionServiceImpl
import java.util.*

class SessionServiceTest {

    private val sessionRepository = Mockito.mock(SessionRepository::class.java)
    private val sessionConverter = Mockito.mock(SessionConverter::class.java)
    private val cinemaHallConverter = Mockito.mock(CinemaHallConverter::class.java)

    private val instance = SessionServiceImpl(sessionRepository, sessionConverter, cinemaHallConverter)

    @Test
    fun `Add Session`() {
        `when`(sessionConverter.createFrom(MockDTO.sessionDto)).thenReturn(MockDTO.session)
        `when`(sessionConverter.createFrom(MockDTO.session)).thenReturn(MockDTO.sessionDto)

        instance.add(MockDTO.sessionDto)

        Mockito.verify(sessionRepository).save(MockDTO.session)
    }

    @Test
    fun `Get session by id` () {
        `when`(sessionRepository.findById(MockDTO.session.id)).thenReturn(Optional.of(MockDTO.session))
        `when`(sessionConverter.createFrom(MockDTO.sessionDto)).thenReturn(MockDTO.session)
        `when`(sessionConverter.createFrom(MockDTO.session)).thenReturn(MockDTO.sessionDto)

        instance.get(MockDTO.session.id)

        Mockito.verify(sessionRepository, times(1)).findById(MockDTO.session.id)
    }

    @Test
    fun `Get all sessions`() {
        `when`(sessionRepository.findAll()).thenReturn(listOf(MockDTO.session))
        `when`(sessionConverter.createFrom(MockDTO.sessionDto)).thenReturn(MockDTO.session)
        `when`(sessionConverter.createFrom(MockDTO.session)).thenReturn(MockDTO.sessionDto)

        instance.get()

        Mockito.verify(sessionRepository, times(1)).findAll()
    }

    @Test
    fun `Update session`() {
        `when`(sessionRepository.getReferenceById(MockDTO.session.id)).thenReturn(MockDTO.session)
        `when`(sessionRepository.findById(MockDTO.session.id)).thenReturn(Optional.of(MockDTO.session))
        `when`(sessionConverter.createFrom(MockDTO.sessionDto)).thenReturn(MockDTO.session)
        `when`(sessionConverter.createFrom(MockDTO.session)).thenReturn(MockDTO.sessionDto)

        instance.update(MockDTO.sessionDto)

        Mockito.verify(sessionRepository, times(1)).save(MockDTO.session)
    }

    @Test
    fun `Delete session`() {
        `when`(sessionRepository.getReferenceById(MockDTO.session.id)).thenReturn(MockDTO.session)
        `when`(sessionRepository.findById(MockDTO.session.id)).thenReturn(Optional.of(MockDTO.session))
        `when`(sessionConverter.createFrom(MockDTO.sessionDto)).thenReturn(MockDTO.session)
        `when`(sessionConverter.createFrom(MockDTO.session)).thenReturn(MockDTO.sessionDto)

        instance.delete(MockDTO.session.id)

        Mockito.verify(sessionRepository, times(1)).deleteById(MockDTO.session.id)
    }

    @Test
    fun `Get available sessions`() {
        `when`(sessionRepository.getAvailableSessions(MockDTO.session.id, MockDTO.session.date)).thenReturn(listOf(MockDTO.session))
        `when`(sessionConverter.createFrom(MockDTO.sessionDto)).thenReturn(MockDTO.session)
        `when`(sessionConverter.createFrom(MockDTO.session)).thenReturn(MockDTO.sessionDto)

        instance.getAvailableSessions(MockDTO.session.id, MockDTO.session.date)

        Mockito.verify(sessionRepository, times(1)).getAvailableSessions(MockDTO.session.id, MockDTO.session.date)
    }
}