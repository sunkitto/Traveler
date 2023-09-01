package com.sunkitto.traveler.common

import javax.inject.Qualifier

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Dispatcher(val travelerDispatcher: TravelerDispatchers)

enum class TravelerDispatchers {
    DEFAULT,
    IO,
}