package com.sunkitto.traveler.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.sunkitto.traveler.R
import com.sunkitto.traveler.data.repository.GoogleAuthRepositoryImpl
import com.sunkitto.traveler.domain.repository.GoogleAuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun provideSignInClient(
        @ApplicationContext context: Context
    ): SignInClient =
        Identity.getSignInClient(context)

    @Provides
    fun provideWebClientId(
        @ApplicationContext context: Context
    ): String =
        context.resources.getString(R.string.your_web_client_id)
}

@Module
@InstallIn(ViewModelComponent::class)
interface AuthBindsModule {

    @Binds
    fun bindGoogleAuthRepository(
        googleAuthRepositoryImpl: GoogleAuthRepositoryImpl
    ): GoogleAuthRepository
}