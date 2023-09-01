package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.domain.repository.CategoriesRepository
import com.sunkitto.traveler.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {

    operator fun invoke(): Flow<Result<List<Category>>> =
        categoriesRepository.getCategories()
}