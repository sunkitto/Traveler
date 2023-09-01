package com.sunkitto.traveler.common

import com.sunkitto.traveler.data.exception.DataExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {

    data class Success<T>(val data: T) : Result<T>

    data class Error(val exception: Throwable? = null) : Result<Nothing>

    object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(exceptionHandler: DataExceptionHandler): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { data ->
            Result.Success(data)
        }
        .onStart {
            emit(Result.Loading)
        }
        .catch { throwable ->
            emit(Result.Error(exceptionHandler.handleException(throwable)))
        }
}