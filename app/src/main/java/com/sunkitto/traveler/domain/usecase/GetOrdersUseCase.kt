package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.domain.repository.OrdersRepository

class GetOrdersUseCase(
    private val ordersRepository: OrdersRepository,
) {

    operator fun invoke() =
        ordersRepository.getOrders()
}