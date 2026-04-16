package com.task_compose.home.presentation.mvi

import org.koin.core.annotation.Single
import com.task_compose.mvi.Reducer
import com.task_compose.utils.orDefault

@Single
class HomeReducer : Reducer<HomeAction, HomeState> {

    override fun reduce(action: HomeAction, state: HomeState): HomeState =
        when (action) {
            is HomeAction.UpdateCategories -> state.copy(
                categories = action.categories,
                categoryId = action.categories.firstOrNull()?.id.orDefault()
            )

            is HomeAction.UpdateProducts -> state.copy(
                products = action.products,
                isLoading = false
            )
            is HomeAction.UpdateQuery -> state.copy(
                query = action.query
            )

            is HomeAction.UpdateListStats -> state.copy(
                listStats = action.listStats,
                isBottomSheetLoading = false
            )

            is HomeAction.ShowBottomSheetLoading -> state.copy(
                isBottomSheetLoading = true
            )

            is HomeAction.ChangeCategoryId -> state.copy(
                categoryId = action.categoryId
            )
        }
}