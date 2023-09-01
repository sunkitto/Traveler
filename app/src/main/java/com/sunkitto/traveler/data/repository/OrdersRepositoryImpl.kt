package com.sunkitto.traveler.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.domain.repository.OrdersRepository
import com.sunkitto.traveler.model.Order
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val ORDERS_COLLECTION = "orders"

class OrdersRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dataErrorHandler: DataExceptionHandler,
) : OrdersRepository {

    override fun getOrders(): Flow<Result<List<Order>>> = callbackFlow {
        trySend(Result.Loading)
        val snapshotListener = firestore.collection(ORDERS_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if(snapshot != null) {
                    val orders: List<Order> = snapshot.toObjects(Order::class.java)
                    Result.Success(data = orders)
                } else {
                    Result.Error(exception = dataErrorHandler.handleException(error))
                }
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun upsertOrder(order: Order): Result<Boolean> =
        try {
            firestore.collection(ORDERS_COLLECTION)
                .document(order.id.toString())
                .set(order)
                .await()
            Result.Success(data = true)
        } catch (exception: Exception) {
            Result.Error(exception = dataErrorHandler.handleException(exception))
        }

    override suspend fun removeOrder(order: Order): Result<Boolean> =
        try {
            firestore.collection(ORDERS_COLLECTION)
                .document(order.id.toString())
                .delete()
                .await()
            Result.Success(data = true)
        } catch (exception: Exception) {
            Result.Error(exception = dataErrorHandler.handleException(exception))
        }
}