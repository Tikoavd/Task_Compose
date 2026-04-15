package com.task_compose.ui_model

import androidx.compose.runtime.Immutable
import com.task_compose.network.entities.CategoryDto
import com.task_compose.utils.orDefault
import kotlinx.serialization.InternalSerializationApi

@Immutable
data class CategoryUI(
    val id: Int,
    val image: String
)

@OptIn(InternalSerializationApi::class)
fun CategoryDto.toUI() = CategoryUI(
    id = id.orDefault(),
    image = image.orEmpty()
)

