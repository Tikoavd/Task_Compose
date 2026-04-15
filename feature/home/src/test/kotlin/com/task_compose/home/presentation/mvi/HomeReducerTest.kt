package com.task_compose.home.presentation.mvi

import androidx.compose.runtime.mutableStateListOf
import com.task_compose.ui_model.CategoryUI
import com.task_compose.ui_model.ListStats
import com.task_compose.ui_model.ProductUI
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeReducerTest {

    private val reducer = HomeReducer()
    private val initialState = HomeState()

    @Test
    fun `UpdateProducts sets products and clears isLoading`() {
        val products = mutableStateListOf(ProductUI(1, "img", "Title", "Sub"))
        val state = initialState.copy(isLoading = true)

        val result = reducer.reduce(HomeAction.UpdateProducts(products), state)

        assertEquals(products, result.products)
        assertFalse(result.isLoading)
    }

    @Test
    fun `UpdateCategories sets categories`() {
        val categories = mutableStateListOf(CategoryUI(1, "img"))

        val result = reducer.reduce(HomeAction.UpdateCategories(categories), initialState)

        assertEquals(categories, result.categories)
    }

    @Test
    fun `UpdateQuery sets query`() {
        val result = reducer.reduce(HomeAction.UpdateQuery("search term"), initialState)

        assertEquals("search term", result.query)
    }

    @Test
    fun `UpdateListStats sets listStats and clears isBottomSheetLoading`() {
        val stats = ListStats(totalItems = 10)
        val state = initialState.copy(isBottomSheetLoading = true)

        val result = reducer.reduce(HomeAction.UpdateListStats(stats), state)

        assertEquals(stats, result.listStats)
        assertFalse(result.isBottomSheetLoading)
    }

    @Test
    fun `ShowBottomSheetLoading sets isBottomSheetLoading to true`() {
        val result = reducer.reduce(HomeAction.ShowBottomSheetLoading, initialState)

        assertTrue(result.isBottomSheetLoading)
    }

    @Test
    fun `reducer does not mutate unrelated state fields`() {
        val state = initialState.copy(query = "existing query", isLoading = false)
        val products = mutableStateListOf<ProductUI>()

        val result = reducer.reduce(HomeAction.UpdateProducts(products), state)

        assertEquals("existing query", result.query)
    }
}