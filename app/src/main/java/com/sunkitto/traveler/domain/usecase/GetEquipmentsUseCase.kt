package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import com.sunkitto.traveler.model.Equipment
import com.sunkitto.traveler.model.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEquipmentsUseCase @Inject constructor(
    private val equipmentsRepository: EquipmentsRepository
) {

    /**
     * Returns list of equipments.
     * @param sortType type of sorting for sorting of equipments. Default [SortType.NONE]: do not sort.
     */
    operator fun invoke(sortType: SortType = SortType.NONE): Flow<Result<List<Equipment>>> {
        val equipments = equipmentsRepository.getEquipments()
        return equipments.map { result ->
            when(result) {
                is Result.Error -> Result.Error(result.exception)
                Result.Loading -> Result.Loading
                is Result.Success -> {
                    Result.Success(
                        when(sortType) {
                            SortType.NONE -> result.data
                            SortType.HIGHEST_PRICE -> result.data.sortedByDescending { it.cost }
                            SortType.LOWEST_PRICE -> result.data.sortedBy { it.cost }
                            SortType.HIGHEST_NAME -> result.data.sortedByDescending { it.name }
                            SortType.LOWEST_NAME -> result.data.sortedBy { it.name }
                        }
                    )
                }
            }
        }
    }

    /**
     * Returns list of equipments by [categoryId].
     * @param categoryId
     */
    operator fun invoke(categoryId: Int) =
        equipmentsRepository.getEquipments(categoryId)
}