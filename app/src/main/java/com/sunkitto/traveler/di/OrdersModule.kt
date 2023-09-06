package com.sunkitto.traveler.di

import com.sunkitto.traveler.data.repository.OrdersRepositoryImpl
import com.sunkitto.traveler.domain.repository.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface OrdersModule {

    @Binds
    fun bindOrdersRepository(
        ordersRepositoryImpl: OrdersRepositoryImpl,
    ): OrdersRepository
}