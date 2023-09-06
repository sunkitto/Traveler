package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import com.sunkitto.traveler.domain.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val equipmentsRepository: EquipmentsRepository,
    private val favouritesRepository: FavouritesRepository,
) {

    /**
     * Returns List of favourite equipments for currently authenticated user.
     */
    operator fun invoke(): Flow<TravelerResult<List<Equipment>>> =
        combine(
            equipmentsRepository.getEquipments(),
            favouritesRepository.getFavourites(),
        ) { equipmentsResult, favouritesResult ->
            when (equipmentsResult) {
                is TravelerResult.Success -> {
                    when (favouritesResult) {
                        is TravelerResult.Success -> {
                            TravelerResult.Success(
                                data = equipmentsResult.data.filter { equipment ->
                                    favouritesResult.data.any { favourite ->
                                        equipment.id == favourite.equipmentId
                                    }
                                },
                            )
                        }
                        is TravelerResult.Error -> {
                            TravelerResult.Error(exception = favouritesResult.exception)
                        }
                        is TravelerResult.Loading -> TravelerResult.Loading
                    }
                }
                is TravelerResult.Error -> {
                    TravelerResult.Error(exception = equipmentsResult.exception)
                }
                TravelerResult.Loading -> TravelerResult.Loading
            }
        }
}