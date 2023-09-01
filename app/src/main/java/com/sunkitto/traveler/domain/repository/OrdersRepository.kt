package com.sunkitto.traveler.domain.repository

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.model.Order
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {

    fun getOrders(): Flow<Result<List<Order>>>

    suspend fun upsertOrder(order: Order): Result<Boolean>

    suspend fun removeOrder(order: Order): Result<Boolean>
}