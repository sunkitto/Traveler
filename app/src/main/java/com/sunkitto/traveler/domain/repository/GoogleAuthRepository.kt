package com.sunkitto.traveler.domain.repository

import android.content.Intent
import android.content.IntentSender
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.model.User
import kotlinx.coroutines.flow.Flow

interface GoogleAuthRepository {

    fun getSignInIntent(): Flow<Result<IntentSender>>

    fun signIn(intent: Intent): Flow<Result<Boolean>>

    fun getUser(): User?

    fun signOut(): Flow<Result<Boolean>>
}