package com.sunkitto.traveler.domain.repository

import android.content.Intent
import android.content.IntentSender
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GoogleAuthRepository {

    fun getSignInIntent(): Flow<TravelerResult<IntentSender>>

    fun signIn(intent: Intent): Flow<TravelerResult<Boolean>>

    fun getUser(): User?

    fun signOut(): Flow<TravelerResult<Boolean>>
}