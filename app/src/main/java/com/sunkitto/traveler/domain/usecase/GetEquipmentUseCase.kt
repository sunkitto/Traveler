package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import com.sunkitto.traveler.domain.repository.FavouritesRepository
import com.sunkitto.traveler.domain.repository.OrdersRepository
import com.sunkitto.traveler.feature.equipmentsDetailed.model.EquipmentUi
import com.sunkitto.traveler.feature.equipmentsDetailed.model.asEquipmentUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetEquipmentUseCase @Inject constructor(
    private val equipmentsRepository: EquipmentsRepository,
    private val favouritesRepository: FavouritesRepository,
    private val ordersRepository: OrdersRepository,
) {

    /**
     * Returns an [EquipmentUi]
     */
    operator fun invoke(equipmentId: String): Flow<TravelerResult<EquipmentUi?>> =
        combine(
            equipmentsRepository.getEquipment(equipmentId = equipmentId),
            favouritesRepository.getFavourite(equipmentId = equipmentId),
            ordersRepository.getOrder(equipmentId = equipmentId),
        ) { equipmentsResult, favouritesResult, ordersResult ->
            when (equipmentsResult) {
                is TravelerResult.Success -> {
                    when (favouritesResult) {
                        is TravelerResult.Success -> {
                            when (ordersResult) {
                                is TravelerResult.Success -> {
                                    TravelerResult.Success(
                                        data = equipmentsResult.data?.asEquipmentUi(
                                            favouriteId = favouritesResult.data?.id,
                                            isFavourite = favouritesResult.data != null,
                                            orderId = ordersResult.data?.id,
                                            isOrdered = ordersResult.data != null,
                                        ),
                                    )
                                }
                                is TravelerResult.Error -> {
                                    TravelerResult.Error(exception = ordersResult.exception)
                                }
                                is TravelerResult.Loading -> TravelerResult.Loading
                            }
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
                is TravelerResult.Loading -> TravelerResult.Loading
            }
        }
}