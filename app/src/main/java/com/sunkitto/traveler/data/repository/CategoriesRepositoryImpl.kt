package com.sunkitto.traveler.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.domain.repository.CategoriesRepository
import com.sunkitto.traveler.model.Category
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

private const val CATEGORIES_COLLECTION = "categories"

class CategoriesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dataErrorHandler: DataExceptionHandler,
) : CategoriesRepository {

    override fun getCategories(): Flow<Result<List<Category>>> =
        callbackFlow {
            val snapshotListener = firestore.collection(CATEGORIES_COLLECTION)
                .addSnapshotListener { snapshot, error ->
                    val response = if(snapshot != null) {
                        val categories: List<Category> = snapshot.toObjects(Category::class.java)
                        Result.Success(data = categories)
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