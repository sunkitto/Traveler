package com.sunkitto.traveler.data.model

import com.sunkitto.traveler.domain.model.Equipment

/**
 * Firebase Firestore representation of [Equipment].
 */
data class EquipmentDoc(
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val cost: Int = 0,
    val categoryId: String = "",
)

/**
 * Maps Firestore [EquipmentDoc] as domain [Equipment].
 * @param id firebase document id.
 */
fun EquipmentDoc.asEquipment(id: String): Equipment =
    Equipment(
        id = id,
        name = this.name,
        image = this.image,
        description = this.description,
        cost = this.cost,
        categoryId = this.categoryId,
    )