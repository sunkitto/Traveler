package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.NoEquipmentsFoundException
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEquipmentsBySearchQueryUseCase @Inject constructor(
    private val equipmentsRepository: EquipmentsRepository,
) {

    /**
     * Returns a Flow of [TravelerResult] that contains one of states below:
     *  - Success: list of [Equipment]'s filtered by [searchQuery].
     *  - Error: exception.
     *  - Loading.
     * @param searchQuery text withing which equipments will be filtered.
     */
    operator fun invoke(searchQuery: String): Flow<TravelerResult<List<Equipment>>> {
        val equipments = equipmentsRepository.getEquipments()

        return equipments.map { result ->
            when (result) {
                is TravelerResult.Success -> {
                    val foundEquipments = result.data.filter { equipment ->
                        equipment.name.contains(searchQuery, true)
                    }
                    if(foundEquipments.isEmpty()) {
                        TravelerResult.Error(NoEquipmentsFoundException())
                    } else {
                        TravelerResult.Success(foundEquipments)
                    }
                }
                is TravelerResult.Error -> TravelerResult.Error(result.exception)
                is TravelerResult.Loading -> TravelerResult.Loading
            }
        }
    }
}