package com.sunkitto.traveler.common

import javax.inject.Qualifier

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Dispatcher(val travelerDispatcher: TravelerDispatchers)

/**
 * Enumeration of dispatchers used in the app.
 */
enum class TravelerDispatchers {
    DEFAULT,
    IO,
}