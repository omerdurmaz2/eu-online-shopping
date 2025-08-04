package com.euonlineshopping.data.repository

import com.euonlineshopping.data.base.BaseRepository
import com.euonlineshopping.data.model.ProductsResponse
import com.euonlineshopping.data.service.ProductService
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val service: ProductService) :
    BaseRepository(), ProductRepository {
    override suspend fun getProducts(sortBy: String?, order: String?): Result<ProductsResponse> =
        request {
            service.getProducts(sortBy, order)
        }
}