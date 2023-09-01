package com.sunkitto.traveler.domain.repository

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.model.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    fun getFavouriteEquipment(): Flow<Result<List<Favourite>>>

    suspend fun setFavoriteEquipment(favourite: Favourite): Result<Boolean>

    suspend fun removeFavoriteEquipment(favourite: Favourite): Result<Boolean>
}