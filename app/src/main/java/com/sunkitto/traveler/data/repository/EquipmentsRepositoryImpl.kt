package com.sunkitto.traveler.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.data.model.EquipmentDoc
import com.sunkitto.traveler.data.model.asEquipment
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

private const val EQUIPMENTS_COLLECTION = "equipments"

private const val CATEGORY_ID_FIELD = "categoryId"

class EquipmentsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dataErrorHandler: DataExceptionHandler,
) : EquipmentsRepository {

    /**
     * Fetches equipments from Firestore equipments collection.
     */
    override fun getEquipments(): Flow<TravelerResult<List<Equipment>>> =
        callbackFlow {
            val snapshotListener = firestore.collection(EQUIPMENTS_COLLECTION)
                .addSnapshotListener { snapshot, exception ->
                    val response = if (snapshot != null) {
                        val equipmentDocs: List<EquipmentDoc> = snapshot.toObjects(
                            EquipmentDoc::class.java,
                        )

                        val equipments = snapshot
                            .documents
                            .zip(equipmentDocs) { document, equipmentDoc ->
                                equipmentDoc.asEquipment(document.id)
                            }

                        TravelerResult.Success(data = equipments)
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
     * Fetches equipments from Firestore equipments collection
     * that are belongs to a specific category.
     * @param categoryId id of category
     */
    override fun getEquipments(categoryId: String): Flow<TravelerResult<List<Equipment>>> =
        callbackFlow {
            val snapshotListener = firestore.collection(EQUIPMENTS_COLLECTION)
                .whereEqualTo(CATEGORY_ID_FIELD, categoryId)
                .addSnapshotListener { snapshot, exception ->
                    val response = if (snapshot != null) {
                        val equipmentDocs: List<EquipmentDoc> = snapshot.toObjects(
                            EquipmentDoc::class.java,
                        )

                        val equipments = snapshot
                            .documents
                            .zip(equipmentDocs) { document, equipmentDoc ->
                                equipmentDoc.asEquipment(document.id)
                            }

                        TravelerResult.Success(data = equipments)
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
     * Fetches single specific equipment from Firestore equipments collection.
     * @param equipmentId id of equipment.
     */
    override fun getEquipment(equipmentId: String): Flow<TravelerResult<Equipment?>> =
        callbackFlow {
            val snapshotListener = firestore.collection(EQUIPMENTS_COLLECTION)
                .document(equipmentId)
                .addSnapshotListener { snapshot, exception ->
                    val response = if (snapshot != null) {
                        val equipmentDoc: EquipmentDoc? = snapshot.toObject(
                            EquipmentDoc::class.java,
                        )

                        if (equipmentDoc == null) {
                            TravelerResult.Success(data = null)
                        } else {
                            val equipment = equipmentDoc.asEquipment(equipmentId)
                            TravelerResult.Success(data = equipment)
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