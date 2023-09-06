package com.sunkitto.traveler.data.model

import com.sunkitto.traveler.domain.model.Category

/**
 * Firebase Firestore representation of [Category].
 */
data class CategoryDoc(
    val name: String = "",
    val image: String = "",
)

/**
 * Maps Firestore [CategoryDoc] as domain [Category].
 * @param id firebase document id.
 */
fun CategoryDoc.asCategory(id: String): Category =
    Category(
        id = id,
        name = this.name,
        image = this.image,
    )