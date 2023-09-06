package com.sunkitto.traveler.feature.cart

sealed interface CartEvent {

    object LoadOrders : CartEvent

    data class OnIncreaseEquipmentsCount(val id: String) : CartEvent

    data class OnDecreaseEquipmentsCount(val id: String) : CartEvent

    data class OnIncreaseRentDays(val id: String) : CartEvent

    data class OnDecreaseRentDays(val id: String) : CartEvent
}