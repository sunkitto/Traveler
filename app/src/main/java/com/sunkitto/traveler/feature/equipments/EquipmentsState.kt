package com.sunkitto.traveler.feature.equipments

import com.sunkitto.traveler.domain.model.Equipment

data class EquipmentsState(
    val equipments: List<Equipment> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val categoryName: String,
)