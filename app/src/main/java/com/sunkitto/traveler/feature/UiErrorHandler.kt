package com.sunkitto.traveler.feature

import com.sunkitto.traveler.R
import com.sunkitto.traveler.common.ResourceManager
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.data.exception.ServiceUnavailableException
import javax.inject.Inject

class UiErrorHandler @Inject constructor(
    private val resourceManager: ResourceManager
) {

    fun handleError(exception: Throwable?): String =
        when(exception) {
            is NoInternetConnectionException ->
                resourceManager.getString(R.string.no_internet_connection)
            is ServiceUnavailableException ->
                resourceManager.getString(R.string.service_unavailable)
            else ->
                resourceManager.getString(R.string.unexpected_error)
        }
}