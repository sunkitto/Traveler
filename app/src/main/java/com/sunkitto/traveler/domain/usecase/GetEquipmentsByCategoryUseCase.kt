package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEquipmentsByCategoryUseCase @Inject constructor(
    private val equipmentsRepository: EquipmentsRepository,
) {

    /**
     * Returns list of equipments by [categoryId].
     * @param categoryId
     */
    operator fun invoke(categoryId: String): Flow<TravelerResult<List<Equipment>>> =
        equipmentsRepository.getEquipments(categoryId)
}