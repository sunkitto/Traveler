package com.sunkitto.traveler.feature.favourites

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.domain.usecase.GetFavouritesUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import com.sunkitto.traveler.model.Equipment
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
class FavouritesViewModelTest {

    private val getFavouritesUseCase = mock<GetFavouritesUseCase>()
    private val uiErrorHandler = mock<UiErrorHandler>()

    private lateinit var favouritesViewModel: FavouritesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        favouritesViewModel = FavouritesViewModel(
            getFavouritesUseCase = getFavouritesUseCase,
            uiErrorHandler = uiErrorHandler,
        )
    }

    @Test
    fun favourites_state_updated_and_returns_success_when_load_favourites_event_submitted() {

        `when`(getFavouritesUseCase())
            .thenReturn(
                flow {
                    emit(Result.Success(data = testEquipments))
                }
            )

        favouritesViewModel.onEvent(FavouritesEvent.LoadFavourites)
        assertEquals(favouritesViewModel.state.value.equipments, testEquipments)
    }

    @Test
    fun favourites_state_updated_and_returns_error_when_load_favourites_event_submitted() {

        `when`(uiErrorHandler.handleError(NoInternetConnectionException()))
            .thenReturn(
                "No Internet Connection"
            )

        `when`(getFavouritesUseCase())
            .thenReturn(
                flow {
                    emit(Result.Error(exception = NoInternetConnectionException()))
                }
            )

        favouritesViewModel.onEvent(FavouritesEvent.LoadFavourites)
        assertEquals(
            "No Internet Connection",
            favouritesViewModel.state.value.errorMessage,
        )
    }

    @Test
    fun favourites_state_updated_and_returns_load_when_load_favourites_event_submitted() {

        `when`(getFavouritesUseCase())
            .thenReturn(
                flow {
                    emit(Result.Loading)
                }
            )

        favouritesViewModel.onEvent(FavouritesEvent.LoadFavourites)
        assertTrue(favouritesViewModel.state.value.isLoading,)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}

private val testEquipments = listOf(
    Equipment(name = "Test")
)