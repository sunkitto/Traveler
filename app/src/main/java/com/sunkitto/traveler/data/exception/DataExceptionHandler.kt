package com.sunkitto.traveler.data.exception

import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class DataExceptionHandler @Inject constructor()  {

    fun handleException(exception: Throwable?): Exception =
        when(exception) {
            is UnknownHostException -> NoInternetConnectionException()
            is IOException -> NoInternetConnectionException()
            else -> ServiceUnavailableException()
        }
}