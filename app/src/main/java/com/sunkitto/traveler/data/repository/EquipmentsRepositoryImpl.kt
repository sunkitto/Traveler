package com.sunkitto.traveler.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import com.sunkitto.traveler.model.Equipment
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

private const val EQUIPMENTS_COLLECTION = "equipments"
private const val CATEGORY_ID_FIELD = "categoryId"

class EquipmentsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dataErrorHandler: DataExceptionHandler,
) : EquipmentsRepository {

    override fun getEquipments(): Flow<Result<List<Equipment>>> =
        callbackFlow {
            val snapshotListener = firestore.collection(EQUIPMENTS_COLLECTION)
                .addSnapshotListener { snapshot, error ->
                    val response = if(snapshot != null) {
                        val equipments: List<Equipment> = snapshot.toObjects(Equipment::class.java)
                        Result.Success(data = equipments)
                    } else {
                        Result.Error(exception = dataErrorHandler.handleException(error))
                    }
                    trySend(element = response)
                }

            awaitClose {
                snapshotListener.remove()
            }
        }
            .onStart {
                emit(Result.Loading)
            }

    override fun getEquipments(categoryId: Int): Flow<Result<List<Equipment>>> =
        callbackFlow {
            val snapshotListener = firestore.collection(EQUIPMENTS_COLLECTION)
                .whereEqualTo(CATEGORY_ID_FIELD, categoryId)
                .addSnapshotListener { snapshot, error ->
                    val response = if(snapshot != null) {
                        val equipments: List<Equipment> = snapshot.toObjects(Equipment::class.java)
                        Result.Success(data = equipments)
                    } else {
                        Result.Error(exception = dataErrorHandler.handleException(error))
                    }
                    trySend(element = response)
                }

            awaitClose {
                snapshotListener.remove()
            }
        }
            .onStart {
                emit(Result.Loading)
            }

    override fun getEquipment(equipmentId: Int): Flow<Result<Equipment>> =
        callbackFlow {
            val snapshotListener = firestore.collection(EQUIPMENTS_COLLECTION)
                .document(equipmentId.toString())
                .addSnapshotListener { snapshot, error ->
                    val response = if(snapshot != null) {
                        val equipments: Equipment = snapshot.toObject(Equipment::class.java)!!
                        Result.Success(data = equipments)
                    } else {
                        Result.Error(exception = dataErrorHandler.handleException(error))
                    }
                    trySend(element = response)
                }

            awaitClose {
                snapshotListener.remove()
            }
        }
            .onStart {
                emit(Result.Loading)
            }
}