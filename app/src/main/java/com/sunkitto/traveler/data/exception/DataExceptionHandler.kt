package com.sunkitto.traveler.data.exception

import com.google.firebase.auth.FirebaseAuthInvalidUserException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Handles exceptions in the data layer.
 */
class DataExceptionHandler @Inject constructor() {

    fun handleException(exception: Throwable?): Exception =
        when (exception) {
            is UnknownHostException -> NoInternetConnectionException()
            is IOException -> NoInternetConnectionException()
            is FirebaseAuthInvalidUserException -> InvalidUserException()
            else -> ServiceUnavailableException()
        }
}