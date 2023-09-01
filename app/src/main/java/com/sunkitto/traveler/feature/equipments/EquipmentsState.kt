package com.sunkitto.traveler.feature.equipments

import com.sunkitto.traveler.model.Equipment

data class EquipmentsState(
    val equipments: List<Equipment>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
