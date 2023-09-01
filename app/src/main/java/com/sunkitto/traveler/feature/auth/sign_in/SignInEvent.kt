package com.sunkitto.traveler.feature.auth.sign_in

import android.content.Intent

sealed interface SignInEvent {

    object SignInIntent : SignInEvent

    class SignInWithGoogle(val signInIntent: Intent) : SignInEvent

}