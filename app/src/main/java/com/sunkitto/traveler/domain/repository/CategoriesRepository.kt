package com.sunkitto.traveler.domain.repository

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun getCategories(): Flow<Result<List<Category>>>
}