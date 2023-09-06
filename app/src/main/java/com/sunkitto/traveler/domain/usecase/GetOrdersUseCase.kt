package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import com.sunkitto.traveler.domain.repository.OrdersRepository
import com.sunkitto.traveler.feature.cart.model.OrderedEquipment
import com.sunkitto.traveler.feature.cart.model.asOrderedEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository,
    private val equipmentsRepository: EquipmentsRepository,
) {

    operator fun invoke(): Flow<TravelerResult<List<OrderedEquipment>>> =
        combine(
            ordersRepository.getOrders(),
            equipmentsRepository.getEquipments(),
        ) { ordersResult, equipmentsResult ->
            when (ordersResult) {
                is TravelerResult.Success -> {
                    when (equipmentsResult) {
                        is TravelerResult.Success -> {
                            val filteredEquipments = equipmentsResult.data.filter { equipment ->
                                ordersResult.data.any { order ->
                                    equipment.id == order.equipmentId
                                }
                            }
                            val orderedEquipments = filteredEquipments
                                .zip(ordersResult.data) { equipment, order ->
                                    equipment.asOrderedEquipment(
                                        orderId = order.id,
                                        count = order.count,
                                        days = order.days,
                                        price = order.price,
                                    )
                                }
                            TravelerResult.Success(
                                data = orderedEquipments,
                            )
                        }
                        is TravelerResult.Error -> {
                            TravelerResult.Error(exception = equipmentsResult.exception)
                        }
                        is TravelerResult.Loading -> TravelerResult.Loading
                    }
                }
                is TravelerResult.Error -> {
                    TravelerResult.Error(exception = ordersResult.exception)
                }
                is TravelerResult.Loading -> TravelerResult.Loading
            }
        }
}