package com.task_compose.home.presentation.mvi

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.task_compose.mvi.MviAction
import com.task_compose.ui_model.CategoryUI
import com.task_compose.ui_model.ListStats
import com.task_compose.ui_model.ProductUI

@Stable
sealed interface HomeAction : MviAction {

    data class UpdateProducts(val products: SnapshotStateList<ProductUI>) : HomeAction
    data class UpdateCategories(val categories: SnapshotStateList<CategoryUI>) : HomeAction
    data class UpdateQuery(val query: String) : HomeAction
    data class UpdateListStats(val listStats: ListStats) : HomeAction
    data object ShowBottomSheetLoading : HomeAction
}
