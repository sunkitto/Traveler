package com.sunkitto.traveler.feature.favourites

import com.sunkitto.traveler.model.Equipment

data class FavouritesState(
    val equipments: List<Equipment>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)