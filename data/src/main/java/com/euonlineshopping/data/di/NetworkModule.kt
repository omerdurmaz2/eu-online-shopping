package com.euonlineshopping.data.di

import com.euonlineshopping.data.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://dummyjson.com/"
    private const val HTTP_CLIENT_DEFAULT_TIMEOUT_SECONDS = 20L

    @Singleton
    @Provides
    fun provideDefaultRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .baseUrl(BASE_URL)
            .build()
    }


    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideRefreshTokenOkHttpClient(
        loggingInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(loggingInterceptor)
        connectTimeout(HTTP_CLIENT_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        readTimeout(HTTP_CLIENT_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }.build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        return interceptor
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDefaultGson(): Gson {
        return GsonBuilder().setLenient().create()
    }
}
