package com.sunkitto.traveler.feature.equipmentsDetailed

import com.sunkitto.traveler.model.Equipment

data class EquipmentsDetailedState(
    val equipment: Equipment? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val equipmentsCount: Int = 0,
    val rentDays: Int = 0,
)
