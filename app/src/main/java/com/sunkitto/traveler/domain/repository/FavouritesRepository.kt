package com.sunkitto.traveler.domain.repository

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    fun getFavourites(): Flow<TravelerResult<List<Favourite>>>

    fun getFavourite(equipmentId: String): Flow<TravelerResult<Favourite?>>

    suspend fun addFavorite(favourite: Favourite): Result<Any>

    suspend fun removeFavorite(favouriteId: String): Result<Any>
}