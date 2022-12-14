package ua.edu.ukma.cinemax.dto.converters.impl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.security.crypto.password.PasswordEncoder
import ua.edu.ukma.cinemax.dto.UserDto
import ua.edu.ukma.cinemax.persistance.entity.Role
import ua.edu.ukma.cinemax.persistance.entity.User
import ua.edu.ukma.cinemax.persistance.repository.RoleRepository

class UserConverterTest {

    private val passwordEncoder = Mockito.mock(PasswordEncoder::class.java)
    private val roleRepository = Mockito.mock(RoleRepository::class.java)
    private val instance = UserConverterImpl(passwordEncoder, roleRepository)

    @Test
    fun `From DTO to Model`() {
        val userDto = UserDto(1, "Test name", "Test email", "Test password", listOf("ROLE_USER"))
        `when`(passwordEncoder.encode(userDto.password)).thenReturn("Encoded password")
        `when`(roleRepository.findByName("ROLE_USER")).thenReturn(Role(1, "USER"))

        val user: User = instance.createFrom(userDto)

        assert(user.id == userDto.id)
        assert(user.username == userDto.username)
        assert(user.email == userDto.email)
        assert(user.password == "Encoded password")
        user.roles.forEach { assert(it.name == "USER") }
    }

    @Test
    fun `From Model to DTO`() {
        val user = User(1, "Test name", "Test email", "Test password", true, listOf(Role(1, "USER")))

        val userDto: UserDto = instance.createFrom(user)

        assert(userDto.id == user.id)
        assert(userDto.username == user.username)
        assert(userDto.email == user.email)
        assert(userDto.password == user.password)
        userDto.roles.forEach { assert(it == "USER") }
    }
}