package com.sunkitto.traveler.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.Dispatcher
import com.sunkitto.traveler.common.TravelerDispatchers
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Order
import com.sunkitto.traveler.domain.repository.OrdersRepository
import com.sunkitto.traveler.domain.usecase.GetOrdersUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import com.sunkitto.traveler.feature.cart.model.asOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val uiErrorHandler: UiErrorHandler,
    @Dispatcher(TravelerDispatchers.DEFAULT) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.LoadOrders -> {
                loadOrders()
            }
            is CartEvent.OnIncreaseEquipmentsCount -> {
                _state.value.orderedEquipments
                    .forEach { orderedEquipment ->
                        if (orderedEquipment.id == event.id && orderedEquipment.count < 5) {
                            updateOrder(
                                orderedEquipment
                                    .copy(count = orderedEquipment.count + 1)
                                    .asOrder()
                            )
                            return@forEach
                        }
                    }
            }
            is CartEvent.OnDecreaseEquipmentsCount -> {
                _state.value.orderedEquipments
                    .forEach { orderedEquipment ->
                        if (orderedEquipment.id == event.id && orderedEquipment.count > 1) {
                            updateOrder(
                                orderedEquipment
                                    .copy(count = orderedEquipment.count - 1)
                                    .asOrder()
                            )
                            return@forEach
                        }
                    }
            }
            is CartEvent.OnIncreaseRentDays -> {
                _state.value.orderedEquipments
                    .forEach { orderedEquipment ->
                        if (orderedEquipment.id == event.id && orderedEquipment.days < 30) {
                            updateOrder(
                                orderedEquipment
                                    .copy(days = orderedEquipment.days + 1)
                                    .asOrder()
                            )
                            return@forEach
                        }
                    }
            }
            is CartEvent.OnDecreaseRentDays -> {
                _state.value.orderedEquipments
                    .forEach { orderedEquipment ->
                        if (orderedEquipment.id == event.id && orderedEquipment.days > 1) {
                            updateOrder(
                                orderedEquipment
                                    .copy(days = orderedEquipment.days - 1)
                                    .asOrder()
                            )
                            return@forEach
                        }
                    }
            }
        }
    }

    private fun loadOrders() {
        getOrdersUseCase()
            .onEach { result ->
                when (result) {
                    is TravelerResult.Success -> {
                        var totalPrice: Deferred<Int>

                        withContext(dispatcher) {
                            totalPrice = async {
                                var price = 0
                                result.data.forEach { orderedEquipment ->
                                    price += orderedEquipment.price *
                                             orderedEquipment.count *
                                            orderedEquipment.days
                                }
                                price
                            }
                        }

                        _state.update { cartState ->
                            cartState.copy(
                                orderedEquipments = result.data,
                                errorMessage = null,
                                isLoading = false,
                                totalPrice = totalPrice.await(),
                            )
                        }
                    }
                    is TravelerResult.Error -> {
                        _state.update { cartState ->
                            cartState.copy(
                                orderedEquipments = emptyList(),
                                errorMessage = uiErrorHandler.handleError(result.exception),
                                isLoading = false,
                            )
                        }
                    }
                    is TravelerResult.Loading -> {
                        _state.update { cartState ->
                            cartState.copy(
                                orderedEquipments = emptyList(),
                                errorMessage = null,
                                isLoading = true,
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun updateOrder(order: Order) {
        viewModelScope.launch {
            ordersRepository.updateOrder(order)
        }
    }
}