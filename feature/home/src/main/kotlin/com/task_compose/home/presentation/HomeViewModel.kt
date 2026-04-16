package com.task_compose.home.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.task_compose.home.domain.usecase.GetCategoriesUseCase
import com.task_compose.home.domain.usecase.GetListStatsUseCase
import com.task_compose.home.domain.usecase.GetProductsUseCase
import com.task_compose.mvi.MviBaseViewModel
import com.task_compose.home.presentation.mvi.HomeAction
import com.task_compose.home.presentation.mvi.HomeAction.*
import com.task_compose.home.presentation.mvi.HomeEffect
import com.task_compose.home.presentation.mvi.HomeIntent
import com.task_compose.home.presentation.mvi.HomeReducer
import com.task_compose.home.presentation.mvi.HomeState
import com.task_compose.ui_model.ListStats
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getListStatsUseCase: GetListStatsUseCase,
    getCategoriesUseCase: GetCategoriesUseCase,
    reducer: HomeReducer
) : MviBaseViewModel<HomeState, HomeAction, HomeIntent, HomeEffect>(HomeState(), reducer) {

    init {
        getCategoriesUseCase().onEach { categories ->
            onAction(UpdateCategories(categories.toMutableStateList()))
        }.catch {
            onAction(UpdateCategories(mutableStateListOf()))
        }.launchIn(viewModelScope)

        viewState
            .debounce(300)
            .onEach {
                getProductsUseCase(it.query, it.categoryId).onEach { products ->
                    onAction(UpdateProducts(products.toMutableStateList()))
                }.catch {
                    onAction(UpdateProducts(mutableStateListOf()))
                }.launchIn(viewModelScope)
            }.launchIn(viewModelScope)
    }

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.GetListStats -> {
                onAction(ShowBottomSheetLoading)
                getListStatsUseCase(viewState.value.products).onEach { listStats ->
                    onAction(UpdateListStats(listStats))
                }.catch {
                    onAction(UpdateListStats(ListStats()))
                }.launchIn(viewModelScope)
            }

            is HomeIntent.Search -> onAction(UpdateQuery(intent.query))
            is HomeIntent.OnCategoryChange -> {
                onAction(ChangeCategoryId(intent.categoryId))
                getProductsUseCase(viewState.value.query, intent.categoryId).onEach { products ->
                    onAction(UpdateProducts(products.toMutableStateList()))
                }.catch {
                    onAction(UpdateProducts(mutableStateListOf()))
                }.launchIn(viewModelScope)
            }
        }
    }
}