package com.task_compose.home.domain.usecase

import com.task_compose.dispatchers.api.DispatchersProvider
import com.task_compose.ui_model.ProductUI
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetListStatsUseCaseTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatchers = mockk<DispatchersProvider> {
        every { default } returns testDispatcher
    }
    private val useCase = GetListStatsUseCaseImpl(dispatchers)

    private fun product(title: String) = ProductUI(0, "", title, "")

    @Test
    fun `empty list returns zero stats`() = runTest {
        val result = useCase(emptyList()).first()

        assertEquals(0, result.totalItems)
        assertEquals(0, result.topChars.size)
    }

    @Test
    fun `counts all letters across product title`() = runTest {
        // "aab" -> 3 letters total
        val result = useCase(listOf(product("aab"))).first()

        assertEquals(3, result.totalItems)
    }

    @Test
    fun `top char is most frequent letter`() = runTest {
        // "aab" -> a=2, b=1
        val result = useCase(listOf(product("aab"))).first()

        assertEquals('a', result.topChars[0].char)
        assertEquals(2, result.topChars[0].count)
    }

    @Test
    fun `returns at most top 3 characters`() = runTest {
        // "aabbccdd" -> 4 distinct chars, only 3 returned
        val result = useCase(listOf(product("aabbccdd"))).first()

        assertEquals(3, result.topChars.size)
    }

    @Test
    fun `ignores non-letter characters`() = runTest {
        // "a1b2c!" -> 3 letters, 3 non-letters ignored
        val result = useCase(listOf(product("a1b2c!"))).first()

        assertEquals(3, result.totalItems)
    }

    @Test
    fun `combines titles from multiple products`() = runTest {
        // "aa" + "bb" + "a" -> "aabba" -> a=3, b=2 -> totalItems=5
        val result = useCase(listOf(product("aa"), product("bb"), product("a"))).first()

        assertEquals(5, result.totalItems)
        assertEquals('a', result.topChars[0].char)
        assertEquals(3, result.topChars[0].count)
    }

    @Test
    fun `comparison is case-insensitive`() = runTest {
        // "Aa" -> lowercased to "aa" -> a=2, totalItems=2
        val result = useCase(listOf(product("Aa"))).first()

        assertEquals(2, result.totalItems)
        assertEquals('a', result.topChars[0].char)
        assertEquals(2, result.topChars[0].count)
    }
}