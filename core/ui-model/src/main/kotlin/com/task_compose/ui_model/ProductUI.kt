package com.task_compose.ui_model

import androidx.compose.runtime.Immutable
import com.task_compose.network.entities.ProductDto
import com.task_compose.utils.orDefault
import kotlinx.serialization.InternalSerializationApi

@Immutable
data class ProductUI(
    val id: Int,
    val categoryId: Int,
    val image: String,
    val title: String,
    val subtitle: String
)

@OptIn(InternalSerializationApi::class)
fun ProductDto.toUI() = ProductUI(
    id = id.orDefault(),
    categoryId = category?.id.orDefault(),
    image = images.orEmpty().firstOrNull().orEmpty(),
    title = title.orEmpty(),
    subtitle = description.orEmpty()
)
