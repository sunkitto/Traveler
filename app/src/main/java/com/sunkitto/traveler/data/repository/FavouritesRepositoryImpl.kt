package com.sunkitto.traveler.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.domain.repository.FavouritesRepository
import com.sunkitto.traveler.model.Favourite
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val FAVOURITES_COLLECTION = "favourites"
private const val USER_ID_FIELD = "userId"

class FavouritesRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataErrorHandler: DataExceptionHandler,
) : FavouritesRepository {

    override fun getFavouriteEquipment(): Flow<Result<List<Favourite>>> =
        callbackFlow {
            val snapshotListener = firestore.collection(FAVOURITES_COLLECTION)
                .whereEqualTo(USER_ID_FIELD, firebaseAuth.uid)
                .addSnapshotListener { snapshot, error ->
                    val response = if (snapshot != null) {
                        val favourites: List<Favourite> =
                            snapshot.toObjects(Favourite::class.java)
                        Result.Success(data = favourites)
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

    override suspend fun setFavoriteEquipment(favourite: Favourite): Result<Boolean> =
        try {
            firestore.collection(FAVOURITES_COLLECTION)
                .document(favourite.id.toString())
                .set(favourite.copy(userId = firebaseAuth.uid!!))
                .await()
            Result.Success(data = true)
        } catch (exception: Exception) {
            Result.Error(exception = dataErrorHandler.handleException(exception))
        }

    override suspend fun removeFavoriteEquipment(favourite: Favourite): Result<Boolean> =
        try {
            firestore.collection(FAVOURITES_COLLECTION)
                .document(favourite.id.toString())
                .delete()
                .await()
            Result.Success(data = true)
        } catch (exception: Exception) {
            Result.Error(exception = dataErrorHandler.handleException(exception))
        }
}