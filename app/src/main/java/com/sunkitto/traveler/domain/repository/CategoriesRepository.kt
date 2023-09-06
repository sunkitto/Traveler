package com.sunkitto.traveler.domain.repository

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun getCategories(): Flow<TravelerResult<List<Category>>>
}