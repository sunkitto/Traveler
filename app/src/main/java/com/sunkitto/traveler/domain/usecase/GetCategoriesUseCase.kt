package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Category
import com.sunkitto.traveler.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
) {

    /**
     * Returns list of categories.
     */
    operator fun invoke(): Flow<TravelerResult<List<Category>>> =
        categoriesRepository.getCategories()
}