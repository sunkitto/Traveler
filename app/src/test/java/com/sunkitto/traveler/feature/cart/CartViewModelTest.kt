package com.sunkitto.traveler.feature.cart

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.data.repository.OrdersRepositoryImpl
import com.sunkitto.traveler.domain.usecase.GetOrdersUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import com.sunkitto.traveler.feature.cart.model.OrderedEquipment
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
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private val getOrdersUseCase = mock<GetOrdersUseCase>()
    private val ordersRepository = mock<OrdersRepositoryImpl>()
    private val uiErrorHandler = mock<UiErrorHandler>()

    private lateinit var cartViewModel: CartViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        cartViewModel = CartViewModel(
            ordersRepository = ordersRepository,
            getOrdersUseCase = getOrdersUseCase,
            uiErrorHandler = uiErrorHandler,
            dispatcher = UnconfinedTestDispatcher(),
        )
    }

    @Test
    fun orders_state_updated_and_returns_success_when_load_orders_event_submitted() {
        `when`(getOrdersUseCase())
            .thenReturn(
                flow {
                    emit(TravelerResult.Success(data = listOf(testOrderedEquipment)))
                },
            )

        cartViewModel.onEvent(CartEvent.LoadOrders)
        assertEquals(cartViewModel.state.value.orderedEquipments, listOf(testOrderedEquipment))
    }

    @Test
    fun orders_state_updated_and_returns_error_when_load_orders_event_submitted() {
        `when`(uiErrorHandler.handleError(ArgumentMatchers.any()))
            .thenReturn(
                "No Internet Connection",
            )

        `when`(getOrdersUseCase())
            .thenReturn(
                flow {
                    emit(TravelerResult.Error(exception = NoInternetConnectionException()))
                },
            )

        cartViewModel.onEvent(CartEvent.LoadOrders)
        assertEquals(
            "No Internet Connection",
            cartViewModel.state.value.errorMessage,
        )
    }

    @Test
    fun orders_state_updated_and_returns_load_when_load_orders_event_submitted() {
        `when`(getOrdersUseCase())
            .thenReturn(
                flow {
                    emit(TravelerResult.Loading)
                },
            )

        cartViewModel.onEvent(CartEvent.LoadOrders)
        Assert.assertTrue(cartViewModel.state.value.isLoading)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}

private val testOrderedEquipment =
    OrderedEquipment(
        id = "",
        name = "",
        image = "",
        description = "",
        cost = 0,
        orderId = "",
        count = 0,
        days = 0,
        price = 0,
    )