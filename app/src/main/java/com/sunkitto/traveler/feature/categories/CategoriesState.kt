package com.sunkitto.traveler.feature.categories

import com.sunkitto.traveler.domain.model.Category

data class CategoriesState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)