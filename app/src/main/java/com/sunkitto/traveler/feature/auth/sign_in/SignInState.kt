package com.sunkitto.traveler.feature.auth.sign_in

import android.content.IntentSender

data class SignInState(
    val isSuccess: Boolean = false,
    val intentSender: IntentSender? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)