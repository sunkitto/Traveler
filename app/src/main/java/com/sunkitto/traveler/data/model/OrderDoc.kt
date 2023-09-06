package com.sunkitto.traveler.data.model

import com.sunkitto.traveler.domain.model.Order

/**
 * Firebase Firestore representation of [Order].
 */
data class OrderDoc(
    val count: Int = 0,
    val days: Int = 0,
    val price: Int = 0,
    val equipmentId: String = "",
    val userId: String = "",
)

/**
 * Maps Firestore [OrderDoc] as domain [Order].
 * @param id firebase document id.
 */
fun OrderDoc.asOrder(id: String): Order =
    Order(
        id = id,
        count = this.count,
        days = this.days,
        price = this.price,
        equipmentId = this.equipmentId,
    )

/**
 * Maps domain [Order] as Firestore [OrderDoc].
 * @param userId id of currently authenticated user.
 */
fun Order.asOrderDoc(userId: String): OrderDoc =
    OrderDoc(
        count = this.count,
        days = this.days,
        price = this.price,
        equipmentId = this.equipmentId,
        userId = userId,
    )