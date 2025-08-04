package com.euonlineshopping.data.service

import com.euonlineshopping.data.model.Category
import com.euonlineshopping.data.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("docs/products")
    suspend fun getProducts(
        @Query("sortBy") sortBy: String?,
        @Query("order") order: String?,
    ): Response<ProductsResponse>

    @GET("docs/products/search")
    suspend fun searchProducts(
        @Query("q") searchTerm: String?,
        @Query("sortBy") sortBy: String?,
        @Query("order") order: String?,
    ): Response<ProductsResponse>

    @GET("docs/products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String,
        @Query("sortBy") sortBy: String?,
        @Query("order") order: String?,
    ): Response<ProductsResponse>


    @GET("docs/products/category-list")
    suspend fun getCategoryList(): Response<Category>
}