package com.euonlineshopping.data.di

import android.content.Context
import com.euonlineshopping.data.datastore.FavoriteProductManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFavoriteProductManager(
        @ApplicationContext context: Context,
        gson: Gson
    ): FavoriteProductManager {
        return FavoriteProductManager(context, gson)
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }
}