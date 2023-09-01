package com.sunkitto.traveler.feature.categories

import com.sunkitto.traveler.model.Category

data class CategoriesState(
    val categories: List<Category>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
