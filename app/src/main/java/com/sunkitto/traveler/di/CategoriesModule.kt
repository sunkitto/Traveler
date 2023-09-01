package com.sunkitto.traveler.di

import com.sunkitto.traveler.data.repository.CategoriesRepositoryImpl
import com.sunkitto.traveler.domain.repository.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CategoriesBindsModule {

    @Binds
    fun bindCategoriesRepository(
        categoriesRepositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository
}