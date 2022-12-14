package ua.edu.ukma.cinemax.dto.service

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import ua.edu.ukma.cinemax.dto.converters.impl.MockDTO
import ua.edu.ukma.cinemax.persistance.entity.ShoppingCart
import ua.edu.ukma.cinemax.persistance.repository.ShoppingCartRepository
import ua.edu.ukma.cinemax.persistance.repository.TicketRepository
import ua.edu.ukma.cinemax.service.ShoppingCartService
import ua.edu.ukma.cinemax.service.UserService
import ua.edu.ukma.cinemax.service.impl.ShoppingCartServiceImpl
import java.util.*

class ShoppingCartService {
    private val shoppingCartRepository = Mockito.mock(ShoppingCartRepository::class.java)
    private val ticketRepository = Mockito.mock(TicketRepository::class.java)
    private val userService = Mockito.mock(UserService::class.java)

    private val instance = ShoppingCartServiceImpl(shoppingCartRepository, ticketRepository, userService)

    @Test
    fun `Get cart by User exist`() {
        `when`(userService.get(MockDTO.user.id)).thenReturn(MockDTO.user)
        `when`(shoppingCartRepository.getByUser(MockDTO.user)).thenReturn(Optional.of(MockDTO.shoppingCart))

        val cart = instance.getByUserId(MockDTO.user.id)
        assert(cart == MockDTO.shoppingCart)
    }

    @Test
    fun `Get cart by User new`() {
        `when`(userService.get(MockDTO.user.id)).thenReturn(MockDTO.user)
        `when`(shoppingCartRepository.getByUser(MockDTO.user)).thenReturn(Optional.empty())
        `when`(shoppingCartRepository.save(ShoppingCart(0, listOf(), MockDTO.user))).thenReturn(ShoppingCart(0, listOf(), MockDTO.user))

        val cart = instance.getByUserId(MockDTO.user.id)
        assert(cart.id == null)
    }

    @Test
    fun `Add session to shopping cart`() {
        val session = MockDTO.session
        `when`(userService.get(MockDTO.user.id)).thenReturn(MockDTO.user)
    }

    @Test
    fun `Clear shopping cart`() {
        `when`(shoppingCartRepository.getReferenceById(MockDTO.shoppingCart.id)).thenReturn(MockDTO.shoppingCart)
        `when`(shoppingCartRepository.save(MockDTO.shoppingCart)).thenReturn(MockDTO.shoppingCart)
        instance.clearShoppingCart(MockDTO.shoppingCart)

        Mockito.verify(shoppingCartRepository, Mockito.times(1)).save(MockDTO.shoppingCart)
    }
}