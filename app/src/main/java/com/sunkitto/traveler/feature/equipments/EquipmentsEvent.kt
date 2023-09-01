package com.sunkitto.traveler.feature.equipments

sealed interface EquipmentsEvent {

    object SortEquipment  : EquipmentsEvent

    data class LoadEquipments(val categoryId: Int) : EquipmentsEvent
}