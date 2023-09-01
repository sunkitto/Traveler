package com.sunkitto.traveler.data.repository

import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sunkitto.traveler.common.Dispatcher
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.common.TravelerDispatchers.IO
import com.sunkitto.traveler.common.asResult
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.domain.repository.GoogleAuthRepository
import com.sunkitto.traveler.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val signInClient: SignInClient,
    private val webClientId: String,
    private val dataExceptionHandler: DataExceptionHandler,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher,
) : GoogleAuthRepository {

    override fun getSignInIntent(): Flow<Result<IntentSender>> =
        flow {
            val result = signInClient.beginSignIn(
                BeginSignInRequest.builder()
                    .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            .setServerClientId(webClientId)
                            .setFilterByAuthorizedAccounts(false)
                            .build())
                    .setAutoSelectEnabled(true)
                    .build()
            ).await()
            emit(result.pendingIntent.intentSender)
        }
            .flowOn(dispatcher)
            .asResult(dataExceptionHandler)

    override fun signIn(intent: Intent): Flow<Result<Boolean>> =
        flow {
            val credential = signInClient.getSignInCredentialFromIntent(intent)
            val googleCredentials = GoogleAuthProvider.getCredential(
                credential.googleIdToken,
                null
            )
            val user = firebaseAuth.signInWithCredential(googleCredentials).await().user
            emit(user != null)
        }
            .flowOn(dispatcher)
            .asResult(dataExceptionHandler)

    override fun getUser(): User? {
        val user = firebaseAuth.currentUser
        return if(user != null) {
            User(
                id = user.uid,
                userName = user.displayName,
                profilePictureUrl = user.photoUrl.toString(),
                email = user.email,
            )
        } else {
            null
        }
    }

    override fun signOut(): Flow<Result<Boolean>> =
        flow {
            signInClient.signOut().await()
            firebaseAuth.currentUser?.apply {
                delete().await()
            }
            emit(true)
        }
            .flowOn(dispatcher)
            .asResult(dataExceptionHandler)
}