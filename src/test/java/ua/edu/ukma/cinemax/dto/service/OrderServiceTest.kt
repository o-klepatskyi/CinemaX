package ua.edu.ukma.cinemax.dto.service

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import ua.edu.ukma.cinemax.dto.converter.impl.FilmConverterImpl
import ua.edu.ukma.cinemax.dto.converters.impl.MockDTO
import ua.edu.ukma.cinemax.persistance.entity.Order
import ua.edu.ukma.cinemax.persistance.repository.FilmRepository
import ua.edu.ukma.cinemax.persistance.repository.OrderRepository
import ua.edu.ukma.cinemax.service.FilmService
import ua.edu.ukma.cinemax.service.ShoppingCartService
import ua.edu.ukma.cinemax.service.impl.FilmServiceImpl
import ua.edu.ukma.cinemax.service.impl.OrderServiceImpl
import java.time.LocalDateTime
import java.util.*

class OrderServiceTest {
    private val orderRepository = Mockito.mock(OrderRepository::class.java)
    private val shoppingCartService = Mockito.mock(ShoppingCartService::class.java)

    private val instance = OrderServiceImpl(orderRepository, shoppingCartService)


    @Test
    fun `Complete Order`() {
        val shoppingCart = MockDTO.shoppingCart
        Mockito.`when`(orderRepository.save(Mockito.any())).thenReturn(MockDTO.order)
        val order = instance.completeOrder(shoppingCart)
        assert(order == MockDTO.order)
    }

    @Test
    fun `Get Order History`() {
        val user = MockDTO.user
        Mockito.`when`(orderRepository.getByUser(user)).thenReturn(listOf())
        val orders = instance.getOrdersHistory(user)
        assert(orders.isEmpty())
    }
}