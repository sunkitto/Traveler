package com.sunkitto.traveler.feature.equipmentsDetailed

sealed interface EquipmentsDetailedEvent {

    data class LoadEquipment(val equipmentId: Int) : EquipmentsDetailedEvent

    object OnIncreaseEquipmentsCount : EquipmentsDetailedEvent

    object OnDecreaseEquipmentsCount : EquipmentsDetailedEvent

    object OnIncreaseRentDays : EquipmentsDetailedEvent

    object OnDecreaseRentDays : EquipmentsDetailedEvent
}