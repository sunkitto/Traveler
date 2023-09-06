package com.sunkitto.traveler.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.data.model.CategoryDoc
import com.sunkitto.traveler.data.model.asCategory
import com.sunkitto.traveler.domain.model.Category
import com.sunkitto.traveler.domain.repository.CategoriesRepository
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

private const val CATEGORIES_COLLECTION = "categories"

class CategoriesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dataErrorHandler: DataExceptionHandler,
) : CategoriesRepository {

    /**
     * Fetches categories from Firestore categories collection.
     */
    override fun getCategories(): Flow<TravelerResult<List<Category>>> =
        callbackFlow {
            val snapshotListener = firestore.collection(CATEGORIES_COLLECTION)
                .addSnapshotListener { snapshot, exception ->
                    val response = if (snapshot != null) {
                        val categoryDocs: List<CategoryDoc> = snapshot.toObjects(
                            CategoryDoc::class.java,
                        )

                        val categories = snapshot
                            .documents
                            .zip(categoryDocs) { document, categoryDoc ->
                                categoryDoc.asCategory(document.id)
                            }

                        TravelerResult.Success(data = categories)
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