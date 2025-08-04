package com.euonlineshopping.data.extensions

import retrofit2.Response


inline val <T> Response<T>.asRestResult: Result<T>
    get() {

        when {
            this.isSuccessful -> {
                return body()?.let { body ->
                    Result.success(body)
                } ?: run {
                    return Result.failure(Exception("Network error"))
                }
            }

            else -> {
                return Result.failure(Exception("Network error"))
            }
        }
    }
