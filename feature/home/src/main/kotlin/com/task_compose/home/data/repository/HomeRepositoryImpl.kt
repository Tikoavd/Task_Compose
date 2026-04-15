package com.task_compose.home.data.repository

import com.task_compose.home.domain.repository.HomeRepository
import com.task_compose.network.api.services.ProductsApi
import com.task_compose.ui_model.CategoryUI
import com.task_compose.ui_model.ProductUI
import com.task_compose.ui_model.toUI
import com.task_compose.utils.emitFlow
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class HomeRepositoryImpl(
    private val productsApi: ProductsApi
) : HomeRepository {

    private var productsFlow: Flow<List<ProductUI>>? = null

    override fun getProducts(): Flow<List<ProductUI>> = productsFlow ?: emitFlow {
        productsApi.getProducts().map { it.toUI() }
    }.also { productsFlow = it }

    override fun getCategories(): Flow<List<CategoryUI>> = emitFlow {
        productsApi.getCategories().map { it.toUI() }
    }

}
