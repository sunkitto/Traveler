package com.sunkitto.traveler.common

import com.sunkitto.traveler.common.TravelerResult.Error
import com.sunkitto.traveler.common.TravelerResult.Loading
import com.sunkitto.traveler.common.TravelerResult.Success
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Wrapper over the data that comes from data or domain layer that encapsulates:
 *  - [Success] - data with a value of type [T].
 *  - [Error] - exception type of [Throwable], can be null.
 *  - [Loading] - loading state.
 *
 */
sealed interface TravelerResult<out T> {

    data class Success<T>(val data: T) : TravelerResult<T>

    data class Error(val exception: Throwable? = null) : TravelerResult<Nothing>

    object Loading : TravelerResult<Nothing>
}

/**
 * Converts Flow as the [TravelerResult] which emits [Loading] on start of the flow, then
 * [Success] with provided data or [Error] with caught exception, which is handled by provided
 * [DataExceptionHandler].
 * @param dataExceptionHandler handles exception that was arisen in the flow.
 */
fun <T> Flow<T>.asResult(dataExceptionHandler: DataExceptionHandler): Flow<TravelerResult<T>> {
    return this
        .map<T, TravelerResult<T>> { data ->
            Success(data = data)
        }
        .onStart {
            emit(value = Loading)
        }
        .catch { throwable ->
            emit(
                Error(exception = dataExceptionHandler.handleException(exception = throwable)),
            )
        }
}