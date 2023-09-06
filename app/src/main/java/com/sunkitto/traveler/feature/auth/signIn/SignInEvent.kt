package com.sunkitto.traveler.feature.auth.signIn

import android.content.Intent

sealed interface SignInEvent {

    object SignInIntent : SignInEvent

    class SignInWithGoogle(val signInIntent: Intent) : SignInEvent
}