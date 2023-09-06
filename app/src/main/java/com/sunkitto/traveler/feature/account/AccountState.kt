package com.sunkitto.traveler.feature.account

import com.sunkitto.traveler.domain.model.User

data class AccountState(
    val user: User = User("", "", "", ""),
    val isSignedOut: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)