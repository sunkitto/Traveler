package com.sunkitto.traveler.feature.cart.model

import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.model.Order

data class OrderedEquipment(
    val id: String,
    val name: String,
    val image: String,
    val description: String,
    val cost: Int,
    val orderId: String,
    val count: Int,
    val days: Int,
    val price: Int,
)

fun Equipment.asOrderedEquipment(
    orderId: String,
    count: Int,
    days: Int,
    price: Int,
) =
    OrderedEquipment(
        id = this.id,
        name = this.name,
        image = this.image,
        description = this.description,
        cost = this.cost,
        orderId = orderId,
        count = count,
        days = days,
        price = price,
    )

fun OrderedEquipment.asOrder() =
    Order(
        id = this.orderId,
        count = this.count,
        days = this.days,
        price = this.price,
        equipmentId = this.id,
    )