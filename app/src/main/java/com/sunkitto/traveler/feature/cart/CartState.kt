package com.sunkitto.traveler.feature.cart

import com.sunkitto.traveler.model.Order

data class CartState(
    val orders: List<Order>? = null,
)