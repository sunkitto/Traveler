package com.sunkitto.traveler.feature.account

import com.sunkitto.traveler.model.User

data class AccountState(
    val user: User? = null,
    val isSignedOut: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
