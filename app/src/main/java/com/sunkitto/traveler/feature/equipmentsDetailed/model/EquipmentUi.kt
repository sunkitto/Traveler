package com.sunkitto.traveler.feature.equipmentsDetailed.model

import com.sunkitto.traveler.domain.model.Equipment

data class EquipmentUi(
    val id: String,
    val name: String,
    val image: String,
    val description: String,
    val cost: Int,
    val categoryId: String,
    val favouriteId: String?,
    val isFavourite: Boolean,
    val orderId: String?,
    val isOrdered: Boolean,
)

fun Equipment.asEquipmentUi(
    favouriteId: String?,
    isFavourite: Boolean,
    orderId: String?,
    isOrdered: Boolean,
) =
    EquipmentUi(
        id = this.id,
        name = this.name,
        image = this.image,
        description = this.description,
        cost = this.cost,
        categoryId = this.categoryId,
        favouriteId = favouriteId,
        isFavourite = isFavourite,
        orderId = orderId,
        isOrdered = isOrdered,
    )