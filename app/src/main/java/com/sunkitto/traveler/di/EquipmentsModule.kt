package com.sunkitto.traveler.di

import com.sunkitto.traveler.data.repository.EquipmentsRepositoryImpl
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface EquipmentsBindsModule {

    @Binds
    fun bindEquipmentsRepository(
        equipmentsRepositoryImpl: EquipmentsRepositoryImpl
    ): EquipmentsRepository
}