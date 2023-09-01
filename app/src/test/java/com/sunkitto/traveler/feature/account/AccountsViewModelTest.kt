package com.sunkitto.traveler.feature.account

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.data.repository.GoogleAuthRepositoryImpl
import com.sunkitto.traveler.feature.UiErrorHandler
import com.sunkitto.traveler.model.User
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

@OptIn(ExperimentalCoroutinesApi::class)
class AccountsViewModelTest {

    private val googleAuthRepository = mock<GoogleAuthRepositoryImpl>()
    private val uiErrorHandler = mock<UiErrorHandler>()

    private lateinit var accountViewModel: AccountViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        accountViewModel = AccountViewModel(
            googleAuthRepository = googleAuthRepository,
            uiErrorHandler = uiErrorHandler,
        )
    }

    @Test
    fun account_state_updated_and_returns_user_when_load_user_event_submitted() {

        `when`(googleAuthRepository.getUser())
            .thenReturn(testUser)

        accountViewModel.onEvent(AccountEvent.LoadUser)
        assertEquals(accountViewModel.state.value.user, testUser)
    }

    @Test
    fun account_state_updated_and_returns_success_when_sign_out_event_submitted() {

        `when`(googleAuthRepository.signOut())
            .thenReturn(
                flow {
                    Result.Success(data = true)
                }
            )

        accountViewModel.onEvent(AccountEvent.SignOut)
        assertTrue(accountViewModel.state.value.isSignedOut)
    }

    @Test
    fun account_state_updated_and_returns_error_when_sign_out_event_submitted() {

        `when`(uiErrorHandler.handleError(NoInternetConnectionException()))
            .thenReturn(
                "No Internet Connection"
            )

        `when`(googleAuthRepository.signOut())
            .thenReturn(
                flow {
                    emit(Result.Error(exception = NoInternetConnectionException()))
                }
            )

        accountViewModel.onEvent(AccountEvent.SignOut)
        assertEquals(
            "No Internet Connection",
            accountViewModel.state.value.errorMessage,
        )
    }

    @Test
    fun account_state_updated_and_returns_load_when_sign_out_event_submitted() {

        `when`(googleAuthRepository.signOut())
            .thenReturn(
                flow {
                    emit(Result.Loading)
                }
            )

        accountViewModel.onEvent(AccountEvent.LoadUser)
        assertTrue(accountViewModel.state.value.isLoading)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}

private val testUser =
    User(
        id = "",
        email = "",
        userName = "",
        profilePictureUrl = ""
    )