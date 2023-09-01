package com.sunkitto.traveler.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sunkitto.traveler.R
import com.sunkitto.traveler.navigation.constants.Route

sealed class BottomBarScreen(
    val route: String,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
) {
    object Categories : BottomBarScreen(
        Route.CATEGORIES,
        R.string.categories,
        R.drawable.ic_category_24,
    )
    object Cart : BottomBarScreen(
        Route.CART,
        R.string.cart,
        R.drawable.ic_shopping_cart_24,
    )
    object Favourites : BottomBarScreen(
        Route.FAVOURITES,
        R.string.favourites,
        R.drawable.ic_star_24,
    )
    object Account : BottomBarScreen(
        Route.ACCOUNT,
        R.string.account,
        R.drawable.ic_person_24,
    )
}