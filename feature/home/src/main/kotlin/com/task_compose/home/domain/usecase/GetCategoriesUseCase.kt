package com.task_compose.home.domain.usecase

import com.task_compose.dispatchers.api.DispatchersProvider
import com.task_compose.home.domain.repository.HomeRepository
import com.task_compose.ui_model.CategoryUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory

interface GetCategoriesUseCase {
    operator fun invoke(): Flow<List<CategoryUI>>
}

@Factory
class GetCategoriesUseCaseImpl(
    private val homeRepository: HomeRepository,
    private val dispatcherProvider: DispatchersProvider
) : GetCategoriesUseCase {
    override fun invoke(): Flow<List<CategoryUI>> = homeRepository.getCategories()
        .flowOn(dispatcherProvider.io)
}
