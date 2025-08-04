package com.euonlineshopping.data.base

import com.euonlineshopping.data.extensions.asRestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

open class BaseRepository : CoroutineScope {

    private var job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    suspend fun <T : Any> request(
        call: suspend () -> Response<T>
    ): Result<T> = withContext(Dispatchers.IO) {
        try {
            call.invoke().asRestResult
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
