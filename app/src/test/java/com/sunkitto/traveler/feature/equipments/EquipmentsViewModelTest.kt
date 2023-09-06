package com.sunkitto.traveler.feature.equipments

import androidx.lifecycle.SavedStateHandle
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.usecase.GetEquipmentsByCategoryUseCase
import com.sunkitto.traveler.domain.usecase.GetEquipmentsUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class EquipmentsViewModelTest {

    private val getEquipmentsUseCase = Mockito.mock<GetEquipmentsUseCase>()
    private val getEquipmentsByCategoryUseCase = Mockito.mock<GetEquipmentsByCategoryUseCase>()
    private val savedStateHandle = SavedStateHandle().apply {
        set("categoryId", "")
        set("categoryName", "")
    }
    private val uiErrorHandler = Mockito.mock<UiErrorHandler>()

    private lateinit var equipmentsViewModel: EquipmentsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        equipmentsViewModel = EquipmentsViewModel(
            getEquipmentsUseCase = getEquipmentsUseCase,
            getEquipmentsByCategoryUseCase = getEquipmentsByCategoryUseCase,
            uiErrorHandler = uiErrorHandler,
            savedStateHandle = savedStateHandle,
        )
    }

    @Test
    fun equipments_state_updated_and_returns_success_when_load_equipments_event_submitted() {
        `when`(getEquipmentsByCategoryUseCase(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Success(data = listOf(testEquipment())))
                },
            )

        equipmentsViewModel.onEvent(EquipmentsEvent.LoadEquipments)
        assertEquals(equipmentsViewModel.state.value.equipments, listOf(testEquipment()))
    }

    @Test
    fun equipments_state_updated_and_returns_error_when_load_equipments_event_submitted() {
        `when`(uiErrorHandler.handleError(ArgumentMatchers.any()))
            .thenReturn(
                "No Internet Connection",
            )

        `when`(getEquipmentsByCategoryUseCase(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Error(exception = NoInternetConnectionException()))
                },
            )

        equipmentsViewModel.onEvent(EquipmentsEvent.LoadEquipments)
        assertEquals(
            "No Internet Connection",
            equipmentsViewModel.state.value.errorMessage,
        )
    }

    @Test
    fun equipments_state_updated_and_returns_load_when_load_equipments_event_submitted() {
        `when`(getEquipmentsByCategoryUseCase(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Loading)
                },
            )

        equipmentsViewModel.onEvent(EquipmentsEvent.LoadEquipments)
        Assert.assertTrue(equipmentsViewModel.state.value.isLoading)
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