package com.sunkitto.traveler.feature.equipmentsDetailed

sealed interface EquipmentsDetailedEvent {

    object LoadEquipment : EquipmentsDetailedEvent

    object OnFavourite : EquipmentsDetailedEvent

    object OnIncreaseEquipmentsCount : EquipmentsDetailedEvent

    object OnDecreaseEquipmentsCount : EquipmentsDetailedEvent

    object OnIncreaseRentDays : EquipmentsDetailedEvent

    object OnDecreaseRentDays : EquipmentsDetailedEvent

    object OnAddToCart : EquipmentsDetailedEvent

    object OnRemoveFromCart : EquipmentsDetailedEvent
}