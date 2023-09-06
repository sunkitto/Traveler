package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.model.SortType
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEquipmentsUseCase @Inject constructor(
    private val equipmentsRepository: EquipmentsRepository,
) {

    /**
     * Returns sorted list of equipments.
     * @param sortType type of sorting for sorting of equipments. Default [SortType.NONE]:
     * do not sort.
     */
    operator fun invoke(sortType: SortType = SortType.NONE): Flow<TravelerResult<List<Equipment>>> {
        val equipments = equipmentsRepository.getEquipments()

        return equipments.map { result ->
            when (result) {
                is TravelerResult.Success -> {
                    TravelerResult.Success(
                        when (sortType) {
                            SortType.NONE -> {
                                result.data
                            }
                            SortType.HIGHEST_PRICE -> result.data.sortedByDescending { equipment ->
                                equipment.cost
                            }
                            SortType.LOWEST_PRICE -> result.data.sortedBy { equipment ->
                                equipment.cost
                            }
                            SortType.HIGHEST_NAME -> result.data.sortedByDescending { equipment ->
                                equipment.name
                            }
                            SortType.LOWEST_NAME -> result.data.sortedBy { equipment ->
                                equipment.name
                            }
                        },
                    )
                }
                is TravelerResult.Error -> TravelerResult.Error(result.exception)
                is TravelerResult.Loading -> TravelerResult.Loading
            }
        }
    }
}