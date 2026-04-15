package com.task_compose.network.entities


import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class ProductDto(
    @SerialName("category")
    val category: CategoryDto? = null,
    @SerialName("creationAt")
    val creationAt: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("images")
    val images: List<String>? = null,
    @SerialName("price")
    val price: Int? = null,
    @SerialName("slug")
    val slug: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null
)
