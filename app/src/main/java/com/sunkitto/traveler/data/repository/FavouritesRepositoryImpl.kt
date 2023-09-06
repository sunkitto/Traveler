package com.sunkitto.traveler.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.data.model.FavouriteDoc
import com.sunkitto.traveler.data.model.asFavourite
import com.sunkitto.traveler.data.model.asFavouriteDoc
import com.sunkitto.traveler.domain.model.Favourite
import com.sunkitto.traveler.domain.repository.FavouritesRepository
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await

private const val FAVOURITES_COLLECTION = "favourites"

private const val EQUIPMENT_ID_FIELD = "equipmentId"
private const val USER_ID_FIELD = "userId"

class FavouritesRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataErrorHandler: DataExceptionHandler,
) : FavouritesRepository {

    /**
     * Fetches favourites from Firestore favourites collection.
     */
    override fun getFavourites(): Flow<TravelerResult<List<Favourite>>> =
        callbackFlow {
            val snapshotListener = firestore.collection(FAVOURITES_COLLECTION)
                .whereEqualTo(USER_ID_FIELD, firebaseAuth.uid)
                .addSnapshotListener { snapshot, exception ->
                    val response = if (snapshot != null) {
                        val favouriteDocs: List<FavouriteDoc> = snapshot.toObjects(
                            FavouriteDoc::class.java,
                        )

                        val favourites = snapshot
                            .documents
                            .zip(favouriteDocs) { document, favouriteDoc ->
                                favouriteDoc.asFavourite(document.id)
                            }

                        TravelerResult.Success(data = favourites)
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
     * Fetches single specific favourite equipment of the current user
     * from Firestore favourites collection.
     * @param equipmentId id of equipment.
     */
    override fun getFavourite(equipmentId: String): Flow<TravelerResult<Favourite?>> =
        callbackFlow {
            val snapshotListener = firestore.collection(FAVOURITES_COLLECTION)
                .whereEqualTo(USER_ID_FIELD, firebaseAuth.uid)
                .whereEqualTo(EQUIPMENT_ID_FIELD, equipmentId)
                .addSnapshotListener { snapshot, exception ->
                    val response = if (snapshot != null) {
                        val favouriteDocs: List<FavouriteDoc> = snapshot.toObjects(
                            FavouriteDoc::class.java,
                        )

                        val favourites = snapshot
                            .documents
                            .zip(favouriteDocs) { document, favouriteDoc ->
                                favouriteDoc.asFavourite(document.id)
                            }

                        if (favourites.isEmpty()) {
                            TravelerResult.Success(data = null)
                        } else {
                            TravelerResult.Success(data = favourites[0])
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

    /**
     * Saves favorite equipment into favourites Firestore collection.
     * @param favourite favourite equipment to save.
     */
    override suspend fun addFavorite(favourite: Favourite): Result<Any> =
        try {
            firestore.collection(FAVOURITES_COLLECTION)
                .add(favourite.asFavouriteDoc(userId = firebaseAuth.currentUser!!.uid))
                .await()
            Result.success(Any())
        } catch (exception: Exception) {
            Result.failure(exception = dataErrorHandler.handleException(exception))
        }

    /**
     * Removes favorite equipment from favourites Firebase collection.
     * @param favouriteId favourite id to remove.
     */
    override suspend fun removeFavorite(favouriteId: String): Result<Any> =
        try {
            firestore.collection(FAVOURITES_COLLECTION)
                .document(favouriteId)
                .delete()
                .await()
            Result.success(Any())
        } catch (exception: Exception) {
            Result.failure(exception = dataErrorHandler.handleException(exception))
        }
}