package com.sunkitto.traveler.feature.favourites

import com.sunkitto.traveler.domain.model.Equipment

data class FavouritesState(
    val equipments: List<Equipment> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)