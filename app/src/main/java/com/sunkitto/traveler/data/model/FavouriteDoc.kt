package com.sunkitto.traveler.data.model

import com.sunkitto.traveler.domain.model.Category
import com.sunkitto.traveler.domain.model.Favourite

/**
 * Firebase Firestore representation of [Favourite].
 */
data class FavouriteDoc(
    val equipmentId: String = "",
    val userId: String = "",
)

/**
 * Maps Firestore [CategoryDoc] as domain [Category].
 * @param id firebase document id.
 */
fun FavouriteDoc.asFavourite(id: String): Favourite =
    Favourite(
        id = id,
        equipmentId = this.equipmentId,
    )

/**
 * Maps domain [Category] as Firestore [CategoryDoc].
 * @param userId id of currently authenticated user.
 */
fun Favourite.asFavouriteDoc(userId: String): FavouriteDoc =
    FavouriteDoc(
        equipmentId = this.equipmentId,
        userId = userId,
    )