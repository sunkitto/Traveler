package com.sunkitto.traveler.feature.search

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.usecase.GetEquipmentsBySearchQueryUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
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
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val getEquipmentsBySearchQueryUseCase = mock<GetEquipmentsBySearchQueryUseCase>()
    private val uiErrorHandler = mock<UiErrorHandler>()

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        searchViewModel = SearchViewModel(
            getEquipmentsBySearchQueryUseCase = getEquipmentsBySearchQueryUseCase,
            uiErrorHandler = uiErrorHandler,
        )
    }

    @Test
    fun search_state_updated_and_returns_success_when_load_search_equipments_event_submitted() {
        `when`(getEquipmentsBySearchQueryUseCase(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Success(data = listOf(testEquipment())))
                },
            )

        searchViewModel.onEvent(SearchEvent.SearchEquipments(""))
        assertEquals(searchViewModel.state.value.equipments, listOf(testEquipment()))
    }

    @Test
    fun search_state_updated_and_returns_error_when_load_search_equipments_submitted() {
        `when`(uiErrorHandler.handleError(ArgumentMatchers.any()))
            .thenReturn(
                "No Internet Connection",
            )

        `when`(getEquipmentsBySearchQueryUseCase(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Error(exception = NoInternetConnectionException()))
                },
            )

        searchViewModel.onEvent(SearchEvent.SearchEquipments(""))
        assertEquals(
            "No Internet Connection",
            searchViewModel.state.value.errorMessage,
        )
    }

    @Test
    fun search_state_updated_and_returns_load_when_load_search_equipments_submitted() {
        `when`(getEquipmentsBySearchQueryUseCase(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Loading)
                },
            )

        searchViewModel.onEvent(SearchEvent.SearchEquipments(any()))
        assertTrue(searchViewModel.state.value.isLoading)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}

private fun testEquipment() =
    Equipment(
        id = "",
        name = "",
        image = "",
        description = "",
        cost = 0,
        categoryId = "",
    )