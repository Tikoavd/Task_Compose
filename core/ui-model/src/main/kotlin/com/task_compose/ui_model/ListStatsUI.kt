package com.task_compose.ui_model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

@Immutable
data class ListStats(
    val totalItems: Int = 0,
    val topChars: SnapshotStateList<TopChar> = mutableStateListOf()
) {
    @Immutable
    data class TopChar(
        val char: Char,
        val count: Int
    )
}
