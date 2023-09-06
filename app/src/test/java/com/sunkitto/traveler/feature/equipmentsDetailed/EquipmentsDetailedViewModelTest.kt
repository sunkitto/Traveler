package com.sunkitto.traveler.feature.equipmentsDetailed

import androidx.lifecycle.SavedStateHandle
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.data.repository.FavouritesRepositoryImpl
import com.sunkitto.traveler.data.repository.OrdersRepositoryImpl
import com.sunkitto.traveler.domain.usecase.GetEquipmentUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import com.sunkitto.traveler.feature.equipmentsDetailed.model.EquipmentUi
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
class EquipmentsDetailedViewModelTest {

    private val getEquipmentUseCase = mock<GetEquipmentUseCase>()
    private val ordersRepository = mock<OrdersRepositoryImpl>()
    private val favouritesRepository = mock<FavouritesRepositoryImpl>()
    private val savedStateHandle = SavedStateHandle().apply {
        set("equipmentId", "")
    }
    private val uiErrorHandler = mock<UiErrorHandler>()

    private lateinit var equipmentsDetailedViewModel: EquipmentsDetailedViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        equipmentsDetailedViewModel = EquipmentsDetailedViewModel(
            getEquipmentUseCase = getEquipmentUseCase,
            ordersRepository = ordersRepository,
            favouritesRepository = favouritesRepository,
            uiErrorHandler = uiErrorHandler,
            savedStateHandle = savedStateHandle,
        )
    }

    @Test
    fun equipments_state_updated_and_returns_success_when_load_equipment_event_submitted() {
        `when`(getEquipmentUseCase(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Success(data = testEquipmentUi))
                },
            )

        equipmentsDetailedViewModel.onEvent(EquipmentsDetailedEvent.LoadEquipment)
        assertEquals(equipmentsDetailedViewModel.state.value.equipment, testEquipmentUi)
    }

    @Test
    fun equipments_state_updated_and_returns_error_when_load_equipment_event_submitted() {
        `when`(uiErrorHandler.handleError(ArgumentMatchers.any()))
            .thenReturn(
                "No Internet Connection",
            )

        `when`(getEquipmentUseCase(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Error(exception = NoInternetConnectionException()))
                },
            )

        equipmentsDetailedViewModel.onEvent(EquipmentsDetailedEvent.LoadEquipment)
        assertEquals(
            "No Internet Connection",
            equipmentsDetailedViewModel.state.value.errorMessage,
        )
    }

    @Test
    fun equipments_state_updated_and_returns_load_when_load_equipment_event_submitted() {
        `when`(getEquipmentUseCase(any()))
            .thenReturn(
                flow {
                    emit(TravelerResult.Loading)
                },
            )

        equipmentsDetailedViewModel.onEvent(EquipmentsDetailedEvent.LoadEquipment)
        assertTrue(equipmentsDetailedViewModel.state.value.isLoading)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}

private val testEquipmentUi =
    EquipmentUi(
        id = "",
        name = "",
        image = "",
        description = "",
        cost = 0,
        categoryId = "",
        favouriteId = null,
        isFavourite = false,
        orderId = null,
        isOrdered = false,
    )