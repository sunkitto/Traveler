package com.sunkitto.traveler.feature.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.domain.repository.GoogleAuthRepository
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository,
    private val uiErrorHandler: UiErrorHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    fun onEvent(accountEvent: AccountEvent) {
        when(accountEvent) {
            is AccountEvent.SignOut -> signOut()
            is AccountEvent.LoadUser -> loadUser()
        }
    }

    private fun loadUser() {
        _state.update { accountState ->
            accountState.copy(
                user = googleAuthRepository.getUser()
            )
        }
    }

    private fun signOut() {
        googleAuthRepository.signOut()
            .onEach { result ->
                when(result) {
                    is Result.Success -> {
                        _state.update { accountState ->
                            accountState.copy(
                                isSignedOut = true,
                                errorMessage = null,
                                isLoading = false,
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update { accountState ->
                            accountState.copy(
                                isSignedOut = false,
                                errorMessage = uiErrorHandler.handleError(result.exception),
                                isLoading = false,
                            )
                        }
                    }
                    is Result.Loading -> {
                        _state.update { accountState ->
                            accountState.copy(
                                isSignedOut = false,
                                errorMessage = null,
                                isLoading = true,
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}