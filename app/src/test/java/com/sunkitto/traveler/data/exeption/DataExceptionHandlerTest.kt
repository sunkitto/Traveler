package com.sunkitto.traveler.data.exeption

import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException

class DataExceptionHandlerTest {

    private lateinit var dataExceptionHandler: DataExceptionHandler

    @Before
    fun setup() {
        dataExceptionHandler = DataExceptionHandler()
    }

    @Test
    fun should_return_no_internet_connection_exception_on_unknown_host_exception() {
        val result = dataExceptionHandler.handleException(exception = UnknownHostException())
        assertTrue(result is NoInternetConnectionException)
    }

    @Test
    fun should_return_no_internet_connection_exception_on_io_exception() {
        val result = dataExceptionHandler.handleException(exception = IOException())
        assertTrue(result is NoInternetConnectionException)
    }
}