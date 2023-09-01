package com.sunkitto.traveler.di

import com.sunkitto.traveler.data.repository.FavouritesRepositoryImpl
import com.sunkitto.traveler.domain.repository.FavouritesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface FavouritesBindsModule {

    @Binds
    fun bindFavouritesRepository(
        favouritesRepositoryImpl: FavouritesRepositoryImpl
    ): FavouritesRepository
}