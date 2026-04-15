package com.task_compose.home.domain.usecase

import com.task_compose.dispatchers.api.DispatchersProvider
import com.task_compose.home.domain.repository.HomeRepository
import com.task_compose.ui_model.ProductUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

interface GetProductsUseCase {
    operator fun invoke(query: String): Flow<List<ProductUI>>
}

@Factory
class GetProductsUseCaseImpl(
    private val homeRepository: HomeRepository,
    private val dispatcherProvider: DispatchersProvider
) : GetProductsUseCase {
    override fun invoke(query: String): Flow<List<ProductUI>> = homeRepository.getProducts()
        .flowOn(dispatcherProvider.io)
        .map { list ->
            list.filter { it.title.lowercase().contains(query.lowercase()) }
        }
        .flowOn(dispatcherProvider.default)
}
