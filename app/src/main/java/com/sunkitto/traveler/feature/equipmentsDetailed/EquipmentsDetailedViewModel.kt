package com.sunkitto.traveler.feature.equipmentsDetailed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.domain.usecase.GetEquipmentUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EquipmentsDetailedViewModel @Inject constructor(
    private val getEquipmentUseCase: GetEquipmentUseCase,
    private val uiErrorHandler: UiErrorHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(EquipmentsDetailedState())
    val state = _state.asStateFlow()

    fun onEvent(event: EquipmentsDetailedEvent) {
        when(event) {
            is EquipmentsDetailedEvent.LoadEquipment -> {
                loadEquipment(equipmentId = event.equipmentId)
            }
            EquipmentsDetailedEvent.OnIncreaseEquipmentsCount -> {
                if(_state.value.equipmentsCount < 5) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            equipmentsCount = equipmentsDetailedState.equipmentsCount + 1
                        )
                    }
                }
            }
            EquipmentsDetailedEvent.OnDecreaseEquipmentsCount -> {
                if(_state.value.equipmentsCount > 1) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            equipmentsCount = equipmentsDetailedState.equipmentsCount - 1
                        )
                    }
                }
            }
            EquipmentsDetailedEvent.OnIncreaseRentDays -> {
                if(_state.value.rentDays < 30) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            rentDays = equipmentsDetailedState.rentDays + 1
                        )
                    }
                }
            }
            EquipmentsDetailedEvent.OnDecreaseRentDays -> {
                if(_state.value.rentDays > 1) {
                    _state.update { equipmentsDetailedState ->
                        equipmentsDetailedState.copy(
                            rentDays = equipmentsDetailedState.rentDays - 1
                        )
                    }
                }
            }
        }
    }

    private fun loadEquipment(equipmentId: Int) {
        getEquipmentUseCase(equipmentId = equipmentId)
            .onEach { result ->
                when(result) {
                    is Result.Success -> {
                        _state.update {
                            EquipmentsDetailedState(
                                equipment = result.data
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update {
                            EquipmentsDetailedState(
                                errorMessage = uiErrorHandler.handleError(result.exception)
                            )
                        }
                    }
                    is Result.Loading -> {
                        _state.update {
                            EquipmentsDetailedState(
                                isLoading = true
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}