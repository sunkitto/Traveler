package com.sunkitto.traveler.feature.search

import com.sunkitto.traveler.model.Equipment

data class SearchState(
    val equipments: List<Equipment>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
