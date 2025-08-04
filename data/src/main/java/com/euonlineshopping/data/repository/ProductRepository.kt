package com.euonlineshopping.data.repository

import com.euonlineshopping.data.model.ProductsResponse

interface ProductRepository {
    suspend fun getProducts(sortBy: String?, order: String?): Result<ProductsResponse>
}