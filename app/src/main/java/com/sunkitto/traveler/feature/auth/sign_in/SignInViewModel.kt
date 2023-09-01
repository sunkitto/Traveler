package com.sunkitto.traveler.feature.auth.sign_in

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.data.repository.GoogleAuthRepositoryImpl
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthRepositoryImpl: GoogleAuthRepositoryImpl,
    private val uiErrorHandler: UiErrorHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.SignInIntent -> {
                loadSignInIntent()
            }
            is SignInEvent.SignInWithGoogle -> {
                signInWithGoogle(event.signInIntent)
            }
        }
    }

    private fun loadSignInIntent() {
        googleAuthRepositoryImpl.getSignInIntent()
            .onEach { result ->
                when(result) {
                    is Result.Success -> _state.update { signInState ->
                        signInState.copy(
                            intentSender = result.data,
                            errorMessage = null,
                            isLoading = false,
                        )
                    }
                    is Result.Loading -> _state.update { signInState ->
                        signInState.copy(
                            intentSender = null,
                            errorMessage = null,
                            isLoading = true,
                        )
                    }
                    is Result.Error -> _state.update { signInState ->
                        signInState.copy(
                            intentSender = null,
                            errorMessage = uiErrorHandler.handleError(result.exception),
                            isLoading = true,
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun signInWithGoogle(intent: Intent) {
        googleAuthRepositoryImpl.signIn(intent)
            .onEach { result ->
                when(result) {
                    is Result.Loading -> _state.update { signInState ->
                        signInState.copy(
                            isSuccess = false,
                            errorMessage = null,
                            isLoading = true
                        )
                    }
                    is Result.Success -> _state.update { signInState ->
                        signInState.copy(
                            isSuccess = result.data,
                            errorMessage = null,
                            isLoading = false
                        )
                    }
                    is Result.Error -> _state.update { signInState ->
                        signInState.copy(
                            isSuccess = false,
                            errorMessage = uiErrorHandler.handleError(result.exception),
                            isLoading = false
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}