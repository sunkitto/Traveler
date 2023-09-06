package com.sunkitto.traveler.domain.repository

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {

    fun getOrders(): Flow<TravelerResult<List<Order>>>

    fun getOrder(equipmentId: String): Flow<TravelerResult<Order?>>

    suspend fun addOrder(order: Order): Result<Any>

    suspend fun updateOrder(order: Order): Result<Any>

    suspend fun removeOrder(orderId: String): Result<Any>
}