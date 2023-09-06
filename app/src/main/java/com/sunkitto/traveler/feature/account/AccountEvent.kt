package com.sunkitto.traveler.feature.account

sealed interface AccountEvent {

    object SignOut : AccountEvent

    object LoadUser : AccountEvent
}