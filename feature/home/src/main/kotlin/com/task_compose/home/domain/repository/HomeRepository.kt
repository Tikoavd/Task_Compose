package com.task_compose.home.domain.repository

import com.task_compose.ui_model.CategoryUI
import com.task_compose.ui_model.ProductUI
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getProducts(): Flow<List<ProductUI>>

    fun getCategories(): Flow<List<CategoryUI>>
}
