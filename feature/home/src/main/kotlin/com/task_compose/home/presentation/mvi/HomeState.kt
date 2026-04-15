package com.task_compose.home.presentation.mvi

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.task_compose.mvi.MviState
import com.task_compose.ui_model.CategoryUI
import com.task_compose.ui_model.ListStats
import com.task_compose.ui_model.ProductUI

@Immutable
data class HomeState(
    val isLoading: Boolean = true,
    val categories: SnapshotStateList<CategoryUI> = mutableStateListOf(),
    val products: SnapshotStateList<ProductUI> = mutableStateListOf(),
    val query: String = "",
    val listStats: ListStats = ListStats(),
    val isBottomSheetLoading: Boolean = false
) : MviState
