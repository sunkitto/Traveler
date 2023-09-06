package com.sunkitto.traveler.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.data.model.OrderDoc
import com.sunkitto.traveler.data.model.asOrder
import com.sunkitto.traveler.data.model.asOrderDoc
import com.sunkitto.traveler.domain.model.Order
import com.sunkitto.traveler.domain.repository.OrdersRepository
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await

private const val ORDERS_COLLECTION = "orders"

private const val EQUIPMENT_ID_FIELD = "equipmentId"
private const val USER_ID_FILED = "userId"

class OrdersRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val dataErrorHandler: DataExceptionHandler,
) : OrdersRepository {

    /**
     * Fetches orders from Firestore orders collection.
     */
    override fun getOrders(): Flow<TravelerResult<List<Order>>> =
        callbackFlow {
            val snapshotListener = firestore.collection(ORDERS_COLLECTION)
                .whereEqualTo(USER_ID_FILED, firebaseAuth.currentUser!!.uid)
                .addSnapshotListener { snapshot, exception ->
                    val response = if (snapshot != null) {
                        val orderDocs: List<OrderDoc> = snapshot.toObjects(OrderDoc::class.java)

                        val orders = snapshot
                            .documents
                            .zip(orderDocs) { document, orderDoc ->
                                orderDoc.asOrder(document.id)
                            }

                        TravelerResult.Success(data = orders)
                    } else {
                        TravelerResult.Error(
                            exception = dataErrorHandler.handleException(exception),
                        )
                    }
                    trySend(element = response)
                }

            awaitClose {
                snapshotListener.remove()
            }
        }
            .onStart {
                emit(TravelerResult.Loading)
            }

    /**
     * Saves order into orders Firestore collection.
     * @param order order to save.
     */
    override suspend fun addOrder(order: Order): Result<Any> =
        try {
            firestore.collection(ORDERS_COLLECTION)
                .add(order.asOrderDoc(userId = firebaseAuth.currentUser!!.uid))
                .await()
            Result.success(Any())
        } catch (exception: Exception) {
            Result.failure(exception = dataErrorHandler.handleException(exception))
        }

    /**
     * Updates order in the orders Firestore collection.
     * @param order order to update.
     */
    override suspend fun updateOrder(order: Order): Result<Any> =
        try {
            firestore.collection(ORDERS_COLLECTION)
                .document(order.id)
                .set(order.asOrderDoc(userId = firebaseAuth.currentUser!!.uid))
                .await()
            Result.success(Any())
        } catch (exception: Exception) {
            Result.failure(exception = dataErrorHandler.handleException(exception))
        }

    /**
     * Removes order from orders Firebase collection.
     * @param orderId order id to remove.
     */
    override suspend fun removeOrder(orderId: String): Result<Any> =
        try {
            firestore.collection(ORDERS_COLLECTION)
                .document(orderId)
                .delete()
                .await()
            Result.success(Any())
        } catch (exception: Exception) {
            Result.failure(exception = dataErrorHandler.handleException(exception))
        }

    /**
     * Fetches single specific order from Firestore orders collection.
     * @param equipmentId id of equipment.
     */
    override fun getOrder(equipmentId: String): Flow<TravelerResult<Order?>> =
        callbackFlow {
            val snapshotListener = firestore.collection(ORDERS_COLLECTION)
                .whereEqualTo(USER_ID_FILED, firebaseAuth.currentUser!!.uid)
                .whereEqualTo(EQUIPMENT_ID_FIELD, equipmentId)
                .addSnapshotListener { snapshot, exception ->
                    val response = if (snapshot != null) {
                        val orderDocs: List<OrderDoc> = snapshot.toObjects(
                            OrderDoc::class.java,
                        )

                        val orders = snapshot
                            .documents
                            .zip(orderDocs) { document, orderDoc ->
                                orderDoc.asOrder(document.id)
                            }

                        if (orders.isEmpty()) {
                            TravelerResult.Success(data = null)
                        } else {
                            TravelerResult.Success(data = orders[0])
                        }
                    } else {
                        TravelerResult.Error(
                            exception = dataErrorHandler.handleException(exception),
                        )
                    }
                    trySend(element = response)
                }

            awaitClose {
                snapshotListener.remove()
            }
        }
            .onStart {
                emit(TravelerResult.Loading)
            }
}