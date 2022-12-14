package ua.edu.ukma.cinemax.dto.service

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import ua.edu.ukma.cinemax.dto.converter.UserConverter
import ua.edu.ukma.cinemax.dto.converter.impl.UserConverterImpl
import ua.edu.ukma.cinemax.dto.converters.impl.MockDTO
import ua.edu.ukma.cinemax.persistance.repository.UserRepository
import ua.edu.ukma.cinemax.service.impl.UserServiceImpl

class UserServiceTest {
    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val userConverter = Mockito.mock(UserConverter::class.java)

    private val instance = UserServiceImpl(userRepository, userConverter)

    @Test
    fun `Add user`() {
        Mockito.`when`(userConverter.createFrom(MockDTO.userDto)).thenReturn(MockDTO.user)
        Mockito.`when`(userRepository.save(MockDTO.user)).thenReturn(MockDTO.user)
        instance.add(MockDTO.userDto)
        Mockito.verify(userRepository).save(MockDTO.user)
    }

    @Test
    fun `Get by id`() {
        Mockito.`when`(userRepository.getReferenceById(1)).thenReturn(MockDTO.user)
        val user = instance.get(1)
        assert(user.id == MockDTO.user.id)
    }

    @Test
    fun `Get by username`() {
        Mockito.`when`(userRepository.findByUsername("Test email")).thenReturn(MockDTO.user)
        val user = instance.getByUsername("Test email")
        assert(user.username == MockDTO.user.username)
    }

    @Test
    fun `Get by mail`() {
        Mockito.`when`(userRepository.findByEmail("Test name")).thenReturn(MockDTO.user)
        val user = instance.getByEmail("Test name")
        assert(user.email == MockDTO.user.email)
    }

    @Test
    fun `Get all`() {
        Mockito.`when`(userRepository.findAll()).thenReturn(listOf(MockDTO.user))
        Mockito.`when`(userConverter.createFromEntities(Mockito.any())).thenReturn(listOf(MockDTO.userDto))
        val users = instance.all
        assert(users.size == 1)
    }

    @Test
    fun `Delete user`() {
        instance.delete(1)
        Mockito.verify(userRepository).deleteById(MockDTO.user.id)
    }
}