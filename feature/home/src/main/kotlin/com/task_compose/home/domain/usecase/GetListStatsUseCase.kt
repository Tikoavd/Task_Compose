package com.task_compose.home.domain.usecase

import androidx.compose.runtime.toMutableStateList
import com.task_compose.dispatchers.api.DispatchersProvider
import com.task_compose.ui_model.ListStats
import com.task_compose.ui_model.ProductUI
import com.task_compose.utils.emitFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory

interface GetListStatsUseCase {
    operator fun invoke(products: List<ProductUI>): Flow<ListStats>
}

@Factory
class GetListStatsUseCaseImpl(
    private val dispatchersProvider: DispatchersProvider
) : GetListStatsUseCase {
    override fun invoke(products: List<ProductUI>): Flow<ListStats> = emitFlow {
        var count = 0
        val topChars = products
            .joinToString("") { it.title }
            .lowercase()
            .filter { it.isLetter() }
            .also { count = it.length }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(3)
            .map { ListStats.TopChar(it.key, it.value) }
        ListStats(
            totalItems = count,
            topChars = topChars.toMutableStateList()
        )
    }.flowOn(dispatchersProvider.default)
}
