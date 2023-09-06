package com.sunkitto.traveler.data.repository

import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sunkitto.traveler.common.Dispatcher
import com.sunkitto.traveler.common.TravelerDispatchers.IO
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.common.asResult
import com.sunkitto.traveler.data.exception.DataExceptionHandler
import com.sunkitto.traveler.di.WebClientIdQualifier
import com.sunkitto.traveler.domain.model.User
import com.sunkitto.traveler.domain.repository.GoogleAuthRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class GoogleAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val signInClient: SignInClient,
    @WebClientIdQualifier private val webClientId: String,
    private val dataExceptionHandler: DataExceptionHandler,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher,
) : GoogleAuthRepository {

    /**
     * Returns [IntentSender] for Google Sign In that can be used futher
     * away for showing bottom sheet for choosing Google account.
     */
    override fun getSignInIntent(): Flow<TravelerResult<IntentSender>> =
        flow {
            val result = signInClient.beginSignIn(
                BeginSignInRequest.builder()
                    .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            .setServerClientId(webClientId)
                            .setFilterByAuthorizedAccounts(false)
                            .build(),
                    )
                    .setAutoSelectEnabled(true)
                    .build(),
            ).await()
            emit(result.pendingIntent.intentSender)
        }
            .flowOn(dispatcher)
            .asResult(dataExceptionHandler)

    /**
     * SignIn to [FirebaseAuth] with Google Credentials that are gets from a
     * provided [Intent].
     * @param intent
     */
    override fun signIn(intent: Intent): Flow<TravelerResult<Boolean>> =
        flow {
            val credential = signInClient.getSignInCredentialFromIntent(intent)
            val googleCredentials = GoogleAuthProvider.getCredential(
                credential.googleIdToken,
                null,
            )
            firebaseAuth.signInWithCredential(googleCredentials).await()
            emit(true)
        }
            .flowOn(dispatcher)
            .asResult(dataExceptionHandler)

    /**
     * Returns a current authenticated [User] instance from [FirebaseAuth], can be null.
     */
    override fun getUser(): User? {
        val user = firebaseAuth.currentUser
        return if (user == null) {
            null
        } else {
            User(
                id = user.uid,
                userName = user.displayName ?: "",
                profilePictureUrl = user.photoUrl.toString(),
                email = user.email ?: "",
            )
        }
    }

    /**
     * Sign Outs - resets [SignInClient] state to sing in.
     */
    override fun signOut(): Flow<TravelerResult<Boolean>> =
        flow {
            signInClient.signOut().await()
            emit(true)
        }
            .flowOn(dispatcher)
            .asResult(dataExceptionHandler)
}