package com.euonlineshopping.data.di

import com.euonlineshopping.data.service.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
class ServiceModule {

    @Provides
    fun provideProductsService(defaultRetrofit: Retrofit): ProductService {
        return defaultRetrofit.create(ProductService::class.java)
    }
}