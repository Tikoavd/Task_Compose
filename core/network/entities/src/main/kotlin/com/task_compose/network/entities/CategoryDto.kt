package com.task_compose.network.entities


import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class CategoryDto(
    @SerialName("creationAt")
    val creationAt: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("image")
    val image: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("slug")
    val slug: String? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null
)
