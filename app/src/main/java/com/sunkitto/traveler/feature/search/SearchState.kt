package com.sunkitto.traveler.feature.search

import com.sunkitto.traveler.domain.model.Equipment

data class SearchState(
    val equipments: List<Equipment> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String? = null,
)