package com.task_compose.network.api.services

import com.task_compose.network.entities.CategoryDto
import com.task_compose.network.entities.ProductDto
import retrofit2.http.GET

interface ProductsApi {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>
}
