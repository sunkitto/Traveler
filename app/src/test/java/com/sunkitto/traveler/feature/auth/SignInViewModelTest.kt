package com.sunkitto.traveler.feature.auth

import android.content.Intent
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.data.repository.GoogleAuthRepositoryImpl
import com.sunkitto.traveler.feature.UiErrorHandler
import com.sunkitto.traveler.feature.auth.signIn.SignInEvent
import com.sunkitto.traveler.feature.auth.signIn.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class SignInViewModelTest {

    private val googleAuthRepositoryImpl = mock<GoogleAuthRepositoryImpl>()
    private val uiErrorHandler = mock<UiErrorHandler>()

    private lateinit var signInViewModel: SignInViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        signInViewModel = SignInViewModel(
            googleAuthRepositoryImpl = googleAuthRepositoryImpl,
            uiErrorHandler = uiErrorHandler,
        )
    }

    @Test
    fun sign_in_state_updated_and_returns_success_when_sing_in_with_google_event_submitted() {
        `when`(googleAuthRepositoryImpl.signIn(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Success(data = true))
                },
            )

        signInViewModel.onEvent(SignInEvent.SignInWithGoogle(Intent()))
        assertTrue(signInViewModel.state.value.isSuccess)
    }

    @Test
    fun sign_in_state_updated_and_returns_error_when_sing_in_with_google_submitted() {
        `when`(uiErrorHandler.handleError(any()))
            .thenReturn(
                "No Internet Connection",
            )

        `when`(googleAuthRepositoryImpl.signIn(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Error(exception = NoInternetConnectionException()))
                },
            )

        signInViewModel.onEvent(SignInEvent.SignInWithGoogle(Intent()))
        assertEquals(
            "No Internet Connection",
            signInViewModel.state.value.errorMessage,
        )
    }

    @Test
    fun sign_in_state_updated_and_returns_load_when_sing_in_with_google_submitted() {
        `when`(googleAuthRepositoryImpl.signIn(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Loading)
                },
            )

        signInViewModel.onEvent(SignInEvent.SignInWithGoogle(Intent()))
        assertTrue(signInViewModel.state.value.isLoading)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}