package com.task_compose.home.presentation.mvi

import androidx.compose.runtime.Stable
import com.task_compose.mvi.MviIntent

@Stable
sealed interface HomeIntent : MviIntent {

    data class Search(val query: String) : HomeIntent
    data object GetListStats : HomeIntent
}
