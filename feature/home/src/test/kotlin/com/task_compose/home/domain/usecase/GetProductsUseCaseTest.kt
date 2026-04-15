package com.task_compose.home.domain.usecase

import com.task_compose.dispatchers.api.DispatchersProvider
import com.task_compose.home.domain.repository.HomeRepository
import com.task_compose.ui_model.ProductUI
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProductsUseCaseTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatchers = mockk<DispatchersProvider> {
        every { io } returns testDispatcher
        every { default } returns testDispatcher
    }
    private val repository = mockk<HomeRepository>()
    private val useCase = GetProductsUseCaseImpl(repository, dispatchers)

    private val products = listOf(
        ProductUI(1, "", "Apple Juice", ""),
        ProductUI(2, "", "Orange Soda", ""),
        ProductUI(3, "", "apple pie", ""),
    )

    @Test
    fun `empty query returns all products`() = runTest {
        every { repository.getProducts() } returns flowOf(products)

        val result = useCase("").toList().first()

        assertEquals(3, result.size)
    }

    @Test
    fun `query filters case-insensitively`() = runTest {
        every { repository.getProducts() } returns flowOf(products)

        val result = useCase("apple").toList().first()

        assertEquals(2, result.size)
        assertEquals(listOf(products[0], products[2]), result)
    }

    @Test
    fun `query with no match returns empty list`() = runTest {
        every { repository.getProducts() } returns flowOf(products)

        val result = useCase("xyz").toList().first()

        assertEquals(emptyList<ProductUI>(), result)
    }

    @Test
    fun `uppercase query matches lowercase title`() = runTest {
        every { repository.getProducts() } returns flowOf(products)

        val result = useCase("ORANGE").toList().first()

        assertEquals(1, result.size)
        assertEquals(products[1], result[0])
    }
}