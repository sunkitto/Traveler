package com.sunkitto.traveler.feature.equipments

import com.sunkitto.traveler.domain.model.SortType

sealed interface EquipmentsEvent {

    data class SortEquipment(val sortType: SortType) : EquipmentsEvent

    object LoadEquipments : EquipmentsEvent
}