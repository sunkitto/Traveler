package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import com.sunkitto.traveler.domain.repository.FavouritesRepository
import com.sunkitto.traveler.model.Equipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val equipmentsRepository: EquipmentsRepository,
    private val favouritesRepository: FavouritesRepository,
) {

    operator fun invoke(): Flow<Result<List<Equipment>>> =
        combine(
            equipmentsRepository.getEquipments(),
            favouritesRepository.getFavouriteEquipment()
        ) { equipments, favourites ->
            when(equipments) {
                is Result.Success -> {
                    when(favourites) {
                        is Result.Success -> {
                            var favouriteEquipments = listOf<Equipment>()
                            favourites.data.forEach { favourite ->
                                favouriteEquipments = equipments.data.filter { equipment ->
                                    equipment.id == favourite.equipmentId
                                }
                            }
                            Result.Success(favouriteEquipments)
                        }
                        is Result.Error -> Result.Error(favourites.exception)
                        Result.Loading -> Result.Loading
                    }
                }
                is Result.Error -> Result.Error(equipments.exception)
                Result.Loading -> Result.Loading
            }
        }
}