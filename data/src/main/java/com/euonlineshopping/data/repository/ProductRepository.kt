package com.euonlineshopping.data.repository

import com.euonlineshopping.data.model.Category
import com.euonlineshopping.data.model.ProductsResponse

interface ProductRepository {
    suspend fun getProducts(sortBy: String?, order: String?): Result<ProductsResponse>
    suspend fun getCategoryProducts(
        category: String,
        sortBy: String?,
        order: String?
    ): Result<ProductsResponse>

    suspend fun getFilterCategories(): Result<List<Category>>
}