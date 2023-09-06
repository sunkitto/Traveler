package com.sunkitto.traveler.feature.equipmentsDetailed

import com.sunkitto.traveler.feature.equipmentsDetailed.model.EquipmentUi

data class EquipmentsDetailedState(
    val equipment: EquipmentUi = EquipmentUi(
        id = "", name = "", image = "", description = "", cost = 0,
        categoryId = "", favouriteId = "", isFavourite = false, orderId = "", isOrdered = false,
    ),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val equipmentId: String = "",
    val equipmentsCount: Int = 1,
    val rentDays: Int = 1,
)