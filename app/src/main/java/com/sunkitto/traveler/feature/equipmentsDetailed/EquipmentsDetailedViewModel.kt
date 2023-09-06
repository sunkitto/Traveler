package com.sunkitto.traveler.feature.equipmentsDetailed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Favourite
import com.sunkitto.traveler.domain.model.Order
import com.sunkitto.traveler.domain.repository.FavouritesRepository
import com.sunkitto.traveler.domain.repository.OrdersRepository
import com.sunkitto.traveler.domain.usecase.GetEquipmentUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val EQUIPMENT_ID_ARG = "equipmentId"

class EquipmentsDetailedArgs(val equipmentId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(checkNotNull(savedStateHandle[EQUIPMENT_ID_ARG]) as String)
}

@HiltViewModel
class EquipmentsDetailedViewModel @Inject constructor(
    private val getEquipmentUseCase: GetEquipmentUseCase,
    private val ordersRepository: OrdersRepository,
    private val favouritesRepository: FavouritesRepository,
    private val uiErrorHandler: UiErrorHandler,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val equipmentsDetailedArgs = EquipmentsDetailedArgs(savedStateHandle)

    private val _state = MutableStateFlow(
        EquipmentsDetailedState(equipmentId = equipmentsDetailedArgs.equipmentId),
    )
    val state = _state.asStateFlow()

    fun onEvent(event: EquipmentsDetailedEvent) {
        when (event) {
            is EquipmentsDetailedEvent.LoadEquipment -> {
                loadEquipment(equipmentId = equipmentsDetailedArgs.equipmentId)
            }
            is EquipmentsDetailedEvent.OnIncreaseEquipmentsCount -> {
                if (_state.value.equipmentsCount < 5) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            equipmentsCount = equipmentsDetailedState.equipmentsCount + 1,
                        )
                    }
                }
            }
            is EquipmentsDetailedEvent.OnDecreaseEquipmentsCount -> {
                if (_state.value.equipmentsCount > 1) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            equipmentsCount = equipmentsDetailedState.equipmentsCount - 1,
                        )
                    }
                }
            }
            is EquipmentsDetailedEvent.OnIncreaseRentDays -> {
                if (_state.value.rentDays < 30) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            rentDays = equipmentsDetailedState.rentDays + 1,
                        )
                    }
                }
            }
            is EquipmentsDetailedEvent.OnDecreaseRentDays -> {
                if (_state.value.rentDays > 1) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            rentDays = equipmentsDetailedState.rentDays - 1,
                        )
                    }
                }
            }
            is EquipmentsDetailedEvent.OnFavourite -> {
                onFavourite()
            }
            is EquipmentsDetailedEvent.OnAddToCart -> {
                addToCart()
            }
            is EquipmentsDetailedEvent.OnRemoveFromCart -> {
                removeFromCart()
            }
        }
    }

    private fun loadEquipment(equipmentId: String) {
        getEquipmentUseCase(equipmentId = equipmentId)
            .onEach { result ->
                when (result) {
                    is TravelerResult.Success -> {
                        if (result.data != null) {
                            _state.update {
                                EquipmentsDetailedState(
                                    equipment = result.data,
                                )
                            }
                        } else {
                            _state.update {
                                EquipmentsDetailedState(
                                    errorMessage = "No equipment",
                                )
                            }
                        }
                    }
                    is TravelerResult.Error -> {
                        _state.update {
                            EquipmentsDetailedState(
                                errorMessage = uiErrorHandler.handleError(result.exception),
                            )
                        }
                    }
                    is TravelerResult.Loading -> {
                        _state.update {
                            EquipmentsDetailedState(
                                isLoading = true,
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onFavourite() {
        viewModelScope.launch {
            if (_state.value.equipment.isFavourite) {
                _state.value.equipment.favouriteId?.let { favouriteId ->
                    val result = favouritesRepository.removeFavorite(favouriteId)

                    if (result.isSuccess) {
                        _state.update { equipmentsDetailedState ->
                            equipmentsDetailedState.copy(
                                equipment = equipmentsDetailedState.equipment.copy(
                                    isFavourite = false,
                                ),
                            )
                        }
                    } else {
                        _state.update { equipmentsDetailedState ->
                            equipmentsDetailedState.copy(
                                errorMessage = result.exceptionOrNull()?.message ?: "",
                            )
                        }
                    }
                }
            } else {
                val result = favouritesRepository.addFavorite(
                    favourite = Favourite(
                        id = "",
                        equipmentId = _state.value.equipment.id,
                    ),
                )

                if (result.isSuccess) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            equipment = equipmentsDetailedState.equipment.copy(
                                isFavourite = true,
                            ),
                        )
                    }
                } else {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            errorMessage = result.exceptionOrNull()?.message ?: "",
                        )
                    }
                }
            }
        }
    }

    private fun addToCart() {
        viewModelScope.launch {
            val price = (_state.value.equipmentsCount * _state.value.equipment.cost) *
                _state.value.rentDays

            val result = ordersRepository.addOrder(
                Order(
                    id = "",
                    count = _state.value.equipmentsCount,
                    days = _state.value.rentDays,
                    price = price,
                    equipmentId = _state.value.equipment.id,
                ),
            )

            if (result.isSuccess) {
                _state.update { equipmentsDetailedState ->
                    equipmentsDetailedState.copy(
                        equipment = equipmentsDetailedState.equipment.copy(
                            isOrdered = true,
                        ),
                    )
                }
            } else {
                _state.update { equipmentsDetailedState ->
                    equipmentsDetailedState.copy(
                        errorMessage = result.exceptionOrNull()?.message ?: "",
                    )
                }
            }
        }
    }

    private fun removeFromCart() {
        viewModelScope.launch {
            _state.value.equipment.orderId?.let { orderId ->
                val result = ordersRepository.removeOrder(orderId)

                if (result.isSuccess) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            equipment = equipmentsDetailedState.equipment.copy(
                                isOrdered = false,
                            ),
                        )
                    }
                } else {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            errorMessage = result.exceptionOrNull()?.message ?: "",
                        )
                    }
                }
            }
        }
    }
}