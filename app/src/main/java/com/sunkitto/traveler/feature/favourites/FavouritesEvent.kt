package com.sunkitto.traveler.feature.favourites

sealed interface FavouritesEvent {
    object LoadFavourites: FavouritesEvent
}